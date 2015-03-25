package controllers;

import play.data.Form;
import play.libs.F.Callback0;
import play.mvc.Result;
import play.mvc.WebSocket;
import currency.exchange.broker.ExchangeBroker;
import currency.exchange.broker.ExchangeClient;
import currency.exchange.broker.ExchangeClientWebSocket;
import currency.exchange.configuration.ExchangeConfiguration;
import currency.exchange.forms.FxMessageAddForm;
import currency.exchange.models.FxSearchCriteria;
import currency.exchange.service.FxMessageService;
import views.html.exchangeStatsAkka;



/**
 * Exchange API Controller that integrates with AKKA
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
public class ExchangeAkkaController extends BaseController {
	
	
	private static ExchangeBroker exchangeBroker = 
			ExchangeConfiguration.exchangeBrokerAkka();
	
	private static FxMessageService fxMessageService =
			ExchangeConfiguration.fxMessageService();
	

	public static Result add() {
		return add(exchangeBroker);
	}
	

	 public static Result init() {
		 return ok(exchangeStatsAkka.render(fxMessageService.listFx(
	    			new FxSearchCriteria("EUR", "GBP",0.7471)),"ws"));
	}
	 
	
	public static WebSocket<String> statsWs() {
		return statsWs(exchangeBroker);
	}
	
	
	

}
