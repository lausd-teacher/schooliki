package net.videmantay.server.validation;

import javax.validation.Payload;

public @interface ValidDateFormat {
	
	String message() default ValidationMessages.INVALID_DATE_FORMAT;
	Class<?> [] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
