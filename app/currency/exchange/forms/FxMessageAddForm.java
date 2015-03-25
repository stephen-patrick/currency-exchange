package currency.exchange.forms;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import currency.exchange.models.FxMessage;

public class FxMessageAddForm {

	@Required
	@MaxLength(255)
	private String userId;
	
	@Required
	@MaxLength(3)
	private String currencyFrom;
	
	@Required
	@MaxLength(3)
	private String currencyTo;
	
	@Required
	@Min(value=1)
	private BigDecimal amountSell;
	
	@Required
	@Min(value=1)
	private BigDecimal amountBuy;
	
	@Required
	private BigDecimal rate;
	
	@Required
	private String timePlaced;
	
	@Required
	@MaxLength(3)
	private String orginatingCountry;

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCurrencyFrom() {
		return currencyFrom;
	}
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}
	public String getCurrencyTo() {
		return currencyTo;
	}
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}
	public BigDecimal getAmountSell() {
		return amountSell;
	}
	public void setAmountSell(BigDecimal amountSell) {
		this.amountSell = amountSell;
	}
	public BigDecimal getAmountBuy() {
		return amountBuy;
	}
	public void setAmountBuy(BigDecimal amountBuy) {
		this.amountBuy = amountBuy;
	}		@Min(value=1)
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getTimePlaced() {
		return timePlaced;
	}
	public void setTimePlaced(String timePlaced) {
		this.timePlaced = timePlaced;
	}
	public String getOrginatingCountry() {
		return orginatingCountry;
	}
	public void setOrginatingCountry(String orginatingCountry) {
		this.orginatingCountry = orginatingCountry;
	}
	
	
	
	public FxMessage createFxMessage() {
		return new FxMessage(userId, currencyFrom, currencyTo, 
				amountSell, amountBuy, rate, timePlaced, orginatingCountry);
	}
}