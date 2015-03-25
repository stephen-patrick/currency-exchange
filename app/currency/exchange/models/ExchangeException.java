package currency.exchange.models;

public class ExchangeException extends RuntimeException {

	private static final long serialVersionUID = 3841481859500247777L;

	public ExchangeException(String message, Throwable t) {
		super(message,t);
	}
	
	

}
