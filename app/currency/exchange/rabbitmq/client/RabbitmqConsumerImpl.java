package currency.exchange.rabbitmq.client;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import currency.exchange.models.ExchangeException;
import currency.exchange.models.FxMessage;
import currency.exchange.utils.JsonUtils;

public class RabbitmqConsumerImpl extends BaseRabbitmqBroker implements
		RabbitmqConsumer {

	private String consumerTag = null;
	private FxMessageComsumer fxConsumer;

	public RabbitmqConsumerImpl(String host, int port, int poolSize,
			String exchangeName, FxMessageComsumer fxConsumer) {
		super(host, port, poolSize, exchangeName);
		this.fxConsumer = fxConsumer;
	}

	@Override
	public void start() throws IOException {
		super.start();
		try {

			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, exchangeName, "");
			ChannelConsumer consumer = new ChannelConsumer(fxConsumer, channel);
			consumerTag = channel.basicConsume(queueName, true, consumer);
		} catch (IOException ioe) {
			throw new ExchangeException("Unable to send message", ioe);
		} finally {
			if (channel != null) {

			}
		}
	}

	@Override
	public void shutdown() throws IOException {
		if (consumerTag != null) {
			channel.basicCancel(consumerTag);
		}

		super.shutdown();
	}

	public static class ChannelConsumer extends DefaultConsumer {
		private FxMessageComsumer listener;

		public ChannelConsumer(FxMessageComsumer listener, Channel channel) {
			super(channel);
			this.listener = listener;
		}

		@Override
		public void handleDelivery(String consumerTag, Envelope envelope,
				BasicProperties properties, byte[] body) throws IOException {
			listener.handleMessage(JsonUtils.parse(new String(body),
					FxMessage.class));
		}
	}

}
