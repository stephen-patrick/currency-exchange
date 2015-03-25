package controllers;

import java.util.HashMap;
import java.util.Map;

import play.Logger;
import play.data.Form;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import currency.exchange.broker.ExchangeBroker;
import currency.exchange.broker.ExchangeClient;
import currency.exchange.broker.ExchangeClientWebSocket;
import currency.exchange.forms.FxMessageAddForm;
import currency.exchange.utils.JsonUtils;
import currency.exchange.utils.ValidationUtils;

public class BaseController extends Controller {
	

	protected static Result add(ExchangeBroker exchangeBroker) {
		try {
			Form<FxMessageAddForm> form = Form.form(
					FxMessageAddForm.class).bindFromRequest();
			

			if(form.hasErrors()) {
				return badRequest("Invalid request. Please fix errors", form);
			}
			
			
			FxMessageAddForm addForm = form.get();
			exchangeBroker.processMessage(addForm.createFxMessage());
			return created("Fx message added", addForm.createFxMessage());
		}
		catch(Throwable t) {
			return serverError("Error unable to add FX message", t);
		}
	}
	
	public static WebSocket<String> statsWs(final ExchangeBroker exchangeBroker) {
		return new WebSocket<String>() {
			 public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out){
				final ExchangeClient consumer = new ExchangeClientWebSocket(in, out);
				exchangeBroker.registerClient(consumer);
				in.onClose(new Callback0() {
					public void invoke() throws Throwable {
						exchangeBroker.unRegisterClient(consumer);
					}
				});
	         }
			
		};
	}
	 
	
	
	
	
	
	
	protected static Result created(
			String message, Object model) {
			Map<String, Object> requestResults = new HashMap<String, Object>();
			requestResults.put("message", message);
			requestResults.put("success", true);
			requestResults.put("model", model);
			return created(JsonUtils.toJson(requestResults).toString());
	}
	
	
	protected static Result  badRequest(
		String message,	Form<? extends Object> form) {
		Map<String, Object> requestResults = new HashMap<String, Object>();
		requestResults.put("errorMessage", message);
		requestResults.put("errors", ValidationUtils.createErrorMapFormForm(form));
		return badRequest(JsonUtils.toJson(requestResults).toString());
	}
	
	

	protected static Result serverError(String message,	Throwable t) {
		Logger.error(message,t);
		Map<String, Object> requestResults = new HashMap<String, Object>();
		requestResults.put("errorMessage", message);
		requestResults.put("error", true);
		return internalServerError(JsonUtils.toJson(requestResults).toString());
	}
	

	

}
