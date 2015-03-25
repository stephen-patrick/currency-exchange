package currency.exchange.configuration;

import play.Application;
import play.Logger;
import currency.exchange.broker.ExchangeBroker;
import currency.exchange.broker.ExchangeBrokerAkkaImpl;
import currency.exchange.broker.ExchangeBrokerRabbitmqImpl;
import currency.exchange.models.ExchangeException;
import currency.exchange.rabbitmq.client.RabbitMqProducerImpl;
import currency.exchange.rabbitmq.client.RabbitmqConsumer;
import currency.exchange.rabbitmq.client.RabbitmqConsumerImpl;
import currency.exchange.rabbitmq.client.RabbitmqProducer;
import currency.exchange.service.FxMessageService;
import currency.exchange.service.FxMessageServiceImpl;

public class ExchangeConfiguration {

	private static ExchangeConfigurator configurator = new ExchangeConfiguratorImpl();

	public static void onStart(Application application) {
		configurator.onStart(application);
	}

	public static void onStop(Application application) {
		configurator.onShutdown(application);

	}

	public static ExchangeBroker exchangeBrokerRabbitmq() {
		return configurator.exchangeBrokermq();

	}

	public static ExchangeBroker exchangeBrokerAkka() {
		return configurator.exchangeBrokerAkka();

	}

	public static FxMessageService fxMessageService() {
		return configurator.fxMessageService();
	}

	public static interface ExchangeConfigurator {
		public void onStart(Application application);

		public void onShutdown(Application application);

		public ExchangeBroker exchangeBrokermq();

		public ExchangeBroker exchangeBrokerAkka();

		public FxMessageService fxMessageService();

	}

	public static class ExchangeConfiguratorImpl implements
			ExchangeConfigurator {

		private Application application;
		private RabbitmqConsumer rabbitmqConsumer = null;
		private RabbitmqProducer rabbitmqProducer = null;

		private ExchangeBroker exchangeBroker;
		private ExchangeBroker exchangeBrokerAkka = null;
		private FxMessageService fxMessageService;

		@Override
		public void onStart(Application application) {
			try {
				this.application = application;
				rabbitmqProducer = new RabbitMqProducerImpl(rabbitmqHost(),
						rabbitmqPort(), rabbitmqConsumerPoolSize(),
						rabbitmqExchangeName());
				fxMessageService = new FxMessageServiceImpl();
				exchangeBroker = new ExchangeBrokerRabbitmqImpl(rabbitmqProducer);
				exchangeBrokerAkka = new ExchangeBrokerAkkaImpl();
				rabbitmqConsumer = new RabbitmqConsumerImpl(rabbitmqHost(),
						rabbitmqPort(), rabbitmqConsumerPoolSize(),
						rabbitmqExchangeName(),
						(ExchangeBrokerRabbitmqImpl) exchangeBroker);

			

				rabbitmqConsumer.start();
				rabbitmqProducer.start();

			} catch (Exception e) {
				Logger.error("Error failed to start rabbitmq client", e);
				throw new ExchangeException(
						"Error failed to start rabbitmq client", e);
			}
		}

		@Override
		public void onShutdown(Application application) {
			try {
				rabbitmqConsumer.shutdown();
				rabbitmqProducer.shutdown();
			} catch (Exception e) {
				Logger.error("Error failed to stop rabbitmq client", e);
				throw new ExchangeException(
						"Error failed to stop rabbitmq client", e);
			}
		}

		@Override
		public ExchangeBroker exchangeBrokermq() {
			return exchangeBroker;
		}

		@Override
		public ExchangeBroker exchangeBrokerAkka() {
			return exchangeBrokerAkka;
		}

		@Override
		public FxMessageService fxMessageService() {
			return fxMessageService;
		}

		private int rabbitmqPort() {
			return application.configuration().getInt("rabbitmq.port", 5672);
		}

		private String rabbitmqHost() {
			return application.configuration().getString("rabbitmq.host",
					"localhost");
		}

		private String rabbitmqExchangeName() {
			return application.configuration().getString(
					"rabbitmq.exchangeName", "currencyExchange.fanout");
		}

		private int rabbitmqConsumerPoolSize() {
			return application.configuration().getInt(
					"rabbitmq.consumer.pool.size", 2);
		}

	}

}
