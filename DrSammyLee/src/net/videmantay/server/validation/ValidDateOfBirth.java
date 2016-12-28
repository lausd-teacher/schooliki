package net.videmantay.server.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidDateOfBirthValidator.class })
@Documented
public @interface ValidDateOfBirth {
	
	String message() default ValidationMessages.INVALID_AGE;
	Class<?> [] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
