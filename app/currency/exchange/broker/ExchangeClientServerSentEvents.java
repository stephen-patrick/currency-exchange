package currency.exchange.broker;

import play.libs.EventSource;
import currency.exchange.models.FxMessage;
import currency.exchange.utils.JsonUtils;

public class ExchangeClientServerSentEvents implements ExchangeClient {

	private EventSource eventSource;
	
	public ExchangeClientServerSentEvents(EventSource eventSource) {
		this.eventSource = eventSource;
	}
	
	
	@Override
	public void handleMessage(FxMessage message) {
		eventSource.sendData(JsonUtils.toJson(message));
	}

}
