package currency.exchange.broker;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import currency.exchange.models.FxMessage;
import currency.exchange.rabbitmq.client.RabbitmqProducer;
import currency.exchange.rabbitmq.client.RabbitmqConsumer.FxMessageComsumer;
import currency.exchange.utils.JsonUtils;

public class ExchangeBrokerRabbitmqImpl implements ExchangeBroker,
		FxMessageComsumer {

	protected CopyOnWriteArrayList<ExchangeClient> exchangeClientSubscriberList = new CopyOnWriteArrayList<ExchangeClient>();

	private RabbitmqProducer rabbitmqProducer;

	public ExchangeBrokerRabbitmqImpl(RabbitmqProducer rabbitmqProducer) {
		this.rabbitmqProducer = rabbitmqProducer;
	}

	public void registerClient(ExchangeClient client) {
		exchangeClientSubscriberList.addIfAbsent(client);

	}

	@Override
	public void unRegisterClient(ExchangeClient client) {
		exchangeClientSubscriberList.remove(client);

	}

	@Override
	public void handleMessage(FxMessage message) {
		notifyClients(message);

	}

	private void notifyClients(FxMessage message) {
		List<ExchangeClient> clientList = exchangeClientSubscriberList;

		for (ExchangeClient client : clientList) {
			client.handleMessage(message);
		}
	}

	@Override
	public void processMessage(FxMessage messageToProcess) {
		rabbitmqProducer.sendMessage(JsonUtils.toJson(messageToProcess));

	}

	public RabbitmqProducer getRabbitmqProducer() {
		return rabbitmqProducer;
	}

	public void setRabbitmqProducer(RabbitmqProducer rabbitmqProducer) {
		this.rabbitmqProducer = rabbitmqProducer;
	}

}
