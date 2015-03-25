package currency.exchange.rabbitmq.client;

/**
 * Type for producing (sending) rabbitmq messages
 * 
 * @author stephen
 *
 */
public interface RabbitmqProducer extends RabbitmqBroker {

	public void sendMessage(String message);

}
