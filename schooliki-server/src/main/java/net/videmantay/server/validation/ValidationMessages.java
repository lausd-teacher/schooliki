package net.videmantay.server.validation;

public  class ValidationMessages {

	
	public static final String EMAIL_INVALID ="Account Id must be a valid Email";
	public static final String INVALID_DATE_FORMAT = "Invalid Date format";
	public static final String INVALID_DATE_PERIOD = "end date may not be before start date";
	public static final String INVALID_AGE = "age should be at least five";
	public static final String INVALID_STUDENT_ROLE = "A Student cannot have other roles";
	public static final String INVALID_TEACHER_ROLE = "A Teacher cannot have a Faculty or Student role";
	public static final String INVALID_FACULTY_ROLE = "A Faculty cannot have a Teacher or Student role";
	public static final String INVALID_ROLE_GENERIC = "Invalid roles combination";
	public static final String INVALID_CHARACTERS = "String may not contain - or * charachters and may not contain html";

}
