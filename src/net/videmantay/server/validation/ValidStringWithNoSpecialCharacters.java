package net.videmantay.server.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SafeHtml
@Pattern(regexp = "^[^*-]*$", message="String may not contain - or * charachters and may not contain html")
public @interface ValidStringWithNoSpecialCharacters {

}
