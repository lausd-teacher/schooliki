package net.videmantay.roster.validation;

import java.util.Date;

public class DateRangeValidator extends DateValidator{


	@Override
	public boolean validate(Date... dates) {
		if(dates.length != 2)
			return false;
		
		Date startDate = dates[0];
		Date endDate = dates[1];
		
		if(endDate != null && startDate != null){
			return endDate.before(startDate);
		}
		return false;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}



}
