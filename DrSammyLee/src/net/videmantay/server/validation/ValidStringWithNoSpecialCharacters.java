package net.videmantay.server.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SafeHtml
@Pattern(regexp = "^[^*-]*$")
public @interface ValidStringWithNoSpecialCharacters {
	
	String message() default ValidationMessages.INVALID_CHARACTERS;
	Class<?> [] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
