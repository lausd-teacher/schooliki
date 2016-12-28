package net.videmantay.roster.validation;

public interface HasValidators {

	public void addValidator(Validator validator);

	public boolean validate();

	public void setErrorStyles(boolean validationResult);

}
