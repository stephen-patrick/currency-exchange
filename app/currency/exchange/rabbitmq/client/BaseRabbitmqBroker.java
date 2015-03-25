package currency.exchange.rabbitmq.client;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * Wrapper base class for working with rabbitmq
 * @author stephen
 *
 */
abstract class BaseRabbitmqBroker implements RabbitmqBroker {
	
	protected ConnectionFactory connectionFactory;
	protected Connection connection = null;
	protected ExecutorService eService; 
	protected int poolSize;
	protected String host;
	protected String exchangeName;
	protected int port;
	protected Channel channel;
	
	public BaseRabbitmqBroker(String host, int port, int poolSize, String exchangeName) {
		this.port = port;
		this.poolSize = poolSize;
		this.exchangeName = exchangeName;
		this.host = host;
		
	}
	
	public void start() throws IOException {
		connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(host);
		 	eService = Executors
				.newFixedThreadPool(poolSize);
		connection = connectionFactory.newConnection(eService);
		channel = connection.createChannel();
		channel.exchangeDeclare(exchangeName, "fanout");
	}

	public void shutdown() throws IOException {
		if(channel!=null) {
			channel.close();
		}
		
		if (connection != null) {
			connection.close();
		}
		if(eService!=null) {
			eService.shutdown();
		}
	}
	

}
