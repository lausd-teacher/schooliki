package net.videmantay.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.videmantay.server.user.AppUser;
import net.videmantay.shared.UserRoles;

public class ValidRolesValidator implements ConstraintValidator<ValidRoles, AppUser> {

	@Override
	public void initialize(ValidRoles constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(AppUser appUser, ConstraintValidatorContext context) {
		
		    if(appUser.getRoles().contains(UserRoles.STUDENT)){
		    	//The list can contain only a student
		    	if(appUser.getRoles().size() > 1){
		    		context.disableDefaultConstraintViolation();
	    			context
	    			   .buildConstraintViolationWithTemplate(ValidationMessages.INVALID_STUDENT_ROLE)
	    			   .addConstraintViolation();
		    		  return false;
		    	}
		    	
		    	
		    }else if(appUser.getRoles().contains(UserRoles.TEACHER)){
		    	//Teacher cannot be admin or faculty
		    	for(UserRoles role: appUser.getRoles()){
		    		if(role.equals(UserRoles.FACULTY) || role.equals(UserRoles.STUDENT)){
		    			context.disableDefaultConstraintViolation();
		    			context
		    			   .buildConstraintViolationWithTemplate(ValidationMessages.INVALID_TEACHER_ROLE)
		    			   .addConstraintViolation();
		    			return false;
		    		}
		    	}
		    	
		    	
		    }else if(appUser.getRoles().contains(UserRoles.FACULTY)){
		    	//faculty can not be teacher or student
		    	for(UserRoles role: appUser.getRoles()){
		    		if(role.equals(UserRoles.TEACHER) || role.equals(UserRoles.STUDENT)){
		    			context.disableDefaultConstraintViolation();
		    			context
		    			   .buildConstraintViolationWithTemplate(ValidationMessages.INVALID_FACULTY_ROLE)
		    			   .addConstraintViolation();
		    			
		    			return false;
		    		}
		    	}
		    }
		
		
		return true;
	}

	

}
