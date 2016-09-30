package net.videmantay.server.validation;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.joda.time.Years;




public class ValidDateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, Date>{

	@Override
	public void initialize(ValidDateOfBirth constraintAnnotation) {

		
	}

	@Override
	public boolean isValid(Date date, ConstraintValidatorContext context) {
		
		Date today = new Date();
		if(date.before(today)){
			
			DateTime dateAsJodaFormat = new DateTime(date);
			DateTime todayAsJodaFormat = new DateTime(today);
			
			return Years.yearsBetween(dateAsJodaFormat, todayAsJodaFormat).getYears() > 5 ;
			
			
		}
		
		
		return false;
	}

	

}
