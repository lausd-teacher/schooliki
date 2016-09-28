package net.videmantay.server.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {

	@Override
	public void initialize(ValidDateFormat constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		 SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd");
		 
		 try{
		 formater.parse(value);
		 }catch(ParseException exp){
			 context.disableDefaultConstraintViolation();
			 context
			 .buildConstraintViolationWithTemplate(ValidationMessages.INVALID_DATE_FORMAT)
			 .addConstraintViolation();
			 
			 return true;
		 }
		
		return true;
	}

	

}
