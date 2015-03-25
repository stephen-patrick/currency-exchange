package currency.exchange.rabbitmq.client;

import java.io.IOException;

import play.Logger;
import currency.exchange.models.ExchangeException;

public class RabbitMqProducerImpl extends BaseRabbitmqBroker implements
		RabbitmqProducer {

	public RabbitMqProducerImpl(String host, int port, int poolSize,
			String exchangeName) {
		super(host, port, poolSize, exchangeName);

	}

	public void sendMessage(String message) {
		try {
			channel.basicPublish(exchangeName, "", null, message.getBytes());
		} catch (IOException ioe) {
			throw new ExchangeException("Unable to send message", ioe);
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException ioe) {
					Logger.warn("Enable to close connection", ioe);
				}

			}
		}
	}

}
