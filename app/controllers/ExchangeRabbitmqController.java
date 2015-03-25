package controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Min;

import play.Logger;
import play.data.Form;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.libs.EventSource;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import currency.exchange.broker.ExchangeBroker;
import currency.exchange.broker.ExchangeClient;
import currency.exchange.broker.ExchangeClientWebSocket;
import currency.exchange.configuration.ExchangeConfiguration;
import currency.exchange.forms.FxMessageAddForm;
import currency.exchange.models.FxMessage;
import currency.exchange.models.FxSearchCriteria;
import currency.exchange.service.FxMessageService;
import currency.exchange.utils.JsonUtils;
import currency.exchange.utils.ValidationUtils;
import views.html.exchangeStatsRabbitmq;




/**
 * Exchange API Controller that integrates with rabbitmq.
 * 
 * Note in a production implementation, this controller would be part of a Rest framework
 * that provides a number of functions such as content type negotiation and content type rendering
 * triggered by HTTP request headers.
 * 
 * This controller expects a JSON request and returns a JSON response 
 * no validation on the content type is performed.
 * 
 * 
 * @author stephen
 *
 */
public class ExchangeRabbitmqController  extends BaseController {
	
	
	private static ExchangeBroker exchangeBroker = 
			ExchangeConfiguration.exchangeBrokerRabbitmq();
	
	private static FxMessageService fxMessageService =
			ExchangeConfiguration.fxMessageService();
	

	public static Result add() {
		return add(exchangeBroker);
	}
	

	/**
	 * Web Socket
	 */
	 public static Result init() {
		 return ok(exchangeStatsRabbitmq.render(fxMessageService.listFx(
	    			new FxSearchCriteria("EUR", "GBP",0.7471)),"ws"));
	    
	}
	 
	
	public static WebSocket<String> statsWs() {
		return statsWs(exchangeBroker);
	}
	
	
	public static ExchangeBroker getExchangeBroker() {
		return exchangeBroker;
	}

	public static void setExchangeBroker(ExchangeBroker exchangeBroker) {
		ExchangeRabbitmqController.exchangeBroker = exchangeBroker;
	}

	public static FxMessageService getFxMessageService() {
		return fxMessageService;
	}

	public static void setFxMessageService(FxMessageService fxMessageService) {
		ExchangeRabbitmqController.fxMessageService = fxMessageService;
	}

}
