package net.videmantay.roster.validation;

public abstract class Validator<T> {
	
	public String errorMessage;

    public abstract boolean validate(T...value);

    public abstract String getErrorMessage();

	
}
