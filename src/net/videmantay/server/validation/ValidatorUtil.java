package net.videmantay.server.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorUtil {
	
	static Validator validator;

	
	public static Validator getValidator(){
		if(validator == null){	
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        validator = factory.getValidator();
		}
		return validator;
	}

}
