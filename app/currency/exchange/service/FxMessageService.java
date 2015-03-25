package currency.exchange.service;


import java.util.List;

import currency.exchange.models.FxMessage;
import currency.exchange.models.FxSearchCriteria;

public interface FxMessageService {
	
	public List<FxMessage> listFx(FxSearchCriteria criteria);

}
