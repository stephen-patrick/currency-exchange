package currency.exchange.rabbitmq.client;

import java.io.IOException;

public interface RabbitmqBroker {

	public void start() throws IOException;
	public void shutdown() throws IOException;
}
