package net.videmantay.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.videmantay.server.entity.Roster;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, Roster>{

	@Override
	public void initialize(ValidDateRange constraintAnnotation) {
		
		
	}

	@Override
	public boolean isValid(Roster roster, ConstraintValidatorContext context) {
		
		 if(roster.endDate.compareTo( roster.startDate) <= 0 ){
			 context.disableDefaultConstraintViolation();
			 context
			 .buildConstraintViolationWithTemplate(ValidationMessages.INVALID_DATE_PERIOD)
			 .addConstraintViolation();
			 return false;
		 }
		
		return true;
	}

}
