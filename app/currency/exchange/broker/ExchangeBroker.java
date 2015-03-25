package currency.exchange.broker;

import currency.exchange.models.FxMessage;

public interface ExchangeBroker {
	
	public void registerClient(ExchangeClient consumer);
	public void unRegisterClient(ExchangeClient consumer);
	public void processMessage(FxMessage messageToProcess);
	
}
