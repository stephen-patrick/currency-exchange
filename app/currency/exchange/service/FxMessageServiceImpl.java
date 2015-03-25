package currency.exchange.service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import currency.exchange.models.FxMessage;
import currency.exchange.models.FxSearchCriteria;


/**
 * Fx Message Service which persists and retireves fx messages.
 * 
 * Note this is only a very basic implementation which returns reference data.
 * In a production implementation this would perhaps persist messages to a backend store,
 * search Fx based on a user sell / buy / rate preference
 * @author stephen
 *
 */
public class FxMessageServiceImpl implements FxMessageService {

	private List<FxMessage> sampleDataList = new LinkedList<FxMessage>();
	
	
	public FxMessageServiceImpl() {
		sampleDataList.add(new FxMessage("12345", 
			 "USD", "EUR", new BigDecimal("100"),
			 new BigDecimal("90"), new BigDecimal("0.9"), "14-Mar-2015 9:23:57", "USA"));
		
		sampleDataList.add(new FxMessage("12345", 
				 "EUR", "GBP", new BigDecimal("1000"),
			new BigDecimal("747.10"), new BigDecimal("0.7471"), "14-Mar-2015 9:43:57", "FR"));
		
		
		
		sampleDataList.add(new FxMessage("12345", 
				 "EUR", "USD", new BigDecimal("90"),
				 new BigDecimal("100"), new BigDecimal("1.1111"), "14-Mar-2015 9:23:57", "USA"));
		
		
		sampleDataList.add(new FxMessage("12346", 
				 "USD", "EUR", new BigDecimal("200"),
				 new BigDecimal("180"), new BigDecimal("0.9"), "14-Mar-2015 9:43:57", "USA"));
		
		sampleDataList.add(new FxMessage("12345", 
				 "USD", "EUR", new BigDecimal("100"),
				 new BigDecimal("90"), new BigDecimal("0.9"), "14-Mar-2015 9:23:57", "USA"));
			
		sampleDataList.add(new FxMessage("12347", 
					 "USD", "EUR", new BigDecimal("200"),
				new BigDecimal("180"), new BigDecimal("0.9"), "10-Feb-2015 9:43:57", "FR"));
		
		sampleDataList.add(new FxMessage("12345", 
				 "USD", "EUR", new BigDecimal("200"),
			new BigDecimal("180"), new BigDecimal("0.9"), "10-Jan-2015 9:43:57", "IE"));
		
	}
	
	@Override
	public List<FxMessage> listFx(FxSearchCriteria criteria) {
		return sampleDataList;
	}


}
