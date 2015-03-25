package currency.exchange.models;

import java.math.BigDecimal;

/**
 * A very simple class to model a Fx message containing currency data.
 * Note in real system depending on requirements there could
 *  be models for users, currencies, rates, countries etc.
 * 
 * @author stephen
 *
 */
public class FxMessage {
	
	private String userId;
	private String currencyFrom;
	private String currencyTo;
	private BigDecimal amountSell;
	private BigDecimal amountBuy;
	private BigDecimal rate;
	private String timePlaced;
	private String orginatingCountry;
	
	
	public FxMessage() {
		
	}
	
	public FxMessage(String userId, String fromCurrency, String toCurrency, BigDecimal amountToSell,
			BigDecimal amountToBuy, BigDecimal rate, String timePlaced, String orginatingCountry) {
		
		this.userId = userId;
		this.currencyFrom = fromCurrency;
		this.currencyTo = toCurrency;
		this.amountSell = amountToSell;
		this.amountBuy = amountToBuy;
		this.rate = rate;
		this.timePlaced = timePlaced;
		this.orginatingCountry = orginatingCountry;
	}
	

	public String getUserId() {
		return userId;
	}
	public String getCurrencyFrom() {
		return currencyFrom;
	}
	public String getCurrencyTo() {
		return currencyTo;
	}
	public BigDecimal getAmountSell() {
		return amountSell;
	}
	public BigDecimal getAmountBuy() {
		return amountBuy;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public String getTimePlaced() {
		return timePlaced;
	}
	public String getOrginatingCountry() {
		return orginatingCountry;
	}

	
}
