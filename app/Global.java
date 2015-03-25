import currency.exchange.configuration.ExchangeConfiguration;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {

	public void onStart(Application paramApplication) {
		super.onStart(paramApplication);
		ExchangeConfiguration.onStart(paramApplication);
	}
	
	public void onStop(Application paramApplication) {
		 ExchangeConfiguration.onStop(paramApplication); 
	}
}
