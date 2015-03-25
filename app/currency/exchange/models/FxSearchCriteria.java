package currency.exchange.models;

public class FxSearchCriteria {
	
	private String fromCurrency;
	private String toCurrency;
	private Double maxRate;

	public FxSearchCriteria(String fromCurrency, String toCurrency, Double maxRate) {
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.maxRate = maxRate;
	}
	
	public String getFromCurrency() {
		return fromCurrency;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public Double getMaxRate() {
		return maxRate;
	}


}
