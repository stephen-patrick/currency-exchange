package currency.exchange.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import play.data.Form;
import play.data.validation.ValidationError;

public class ValidationUtils {

	
	public static Map<String, List<String>> createErrorMapFormForm(
			Form<? extends Object> form) {
		Map<String, List<String>> errorMap = new HashMap<String, List<String>>();

		if (!form.hasErrors()) {
			return errorMap;
		}

		Map<String, List<ValidationError>> formErrors = form.errors();

		for (String errorKey : formErrors.keySet()) {
			List<ValidationError> vErrors = formErrors.get(errorKey);

			if (vErrors == null) {
				continue;
			}

			for (ValidationError vError : vErrors) {
				List<String> errors = errorMap.get(errorKey);
				if (errors != null) {
					errors.add(vError.message());
				} else {
					errors = new LinkedList<String>();
					errors.add(vError.message());
					errorMap.put(errorKey, errors);
				}
			}

		}

		return errorMap;
	}
	
	
	
	
}
