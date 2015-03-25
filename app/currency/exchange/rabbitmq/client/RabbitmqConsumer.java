package currency.exchange.rabbitmq.client;

import currency.exchange.models.FxMessage;

public interface RabbitmqConsumer extends RabbitmqBroker {
	
	public static interface FxMessageComsumer {
		public void handleMessage(FxMessage message);
		
	}
	
}



