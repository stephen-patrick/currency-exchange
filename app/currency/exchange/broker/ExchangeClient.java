package currency.exchange.broker;

import currency.exchange.models.FxMessage;

public interface ExchangeClient {
	public void handleMessage(FxMessage message);
}
