package currency.exchange.broker;

import play.mvc.WebSocket;
import currency.exchange.models.FxMessage;
import currency.exchange.utils.JsonUtils;

public class ExchangeClientWebSocket implements ExchangeClient {

	private WebSocket.Out<String> socketOut;
	
	public ExchangeClientWebSocket(WebSocket.In<String> in, WebSocket.Out<String> out) {
		this.socketOut = out;
	
	}

	@Override
	public void handleMessage(FxMessage message) {
		if(message !=null) {
			socketOut.write(JsonUtils.toJson(message));
		}
	}

	
	
}
