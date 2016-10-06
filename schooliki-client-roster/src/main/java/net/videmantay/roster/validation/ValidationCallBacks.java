package net.videmantay.roster.validation;

import static com.google.gwt.query.client.GQuery.$;

import java.util.Date;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.query.client.Function;

import gwt.material.design.client.ui.MaterialDatePicker;

public class ValidationCallBacks {

	public static CloseHandler<MaterialDatePicker> startDatePickerCallback(final Date startDate, final Date endDate) {

		return new CloseHandler<MaterialDatePicker>() {
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
				// TODO Auto-generated method stub
				if (endDate != null && startDate != null) {
					if (endDate.before(startDate)) {
						$("#errorStartDateLabel").show();

						return;
					}
				}
			}
		};

	}
	
	public static CloseHandler<MaterialDatePicker> endDatePickerCallback(final Date startDate, final Date endDate) {

		return new CloseHandler<MaterialDatePicker>(){
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
			    if(startDate != null && endDate != null){
					 
					 if(endDate.before(startDate)){
						 $("#errorEndDateLabel").show();
						 
					 }else{
						 $("#errorEndDateLabel").hide();
						 
					 }
				 }else if(endDate != null){
					 $("#errorEndDateLabel").show();
				 }
			}
		};

	}
	
	
	public static Function HTMLValidationFunction(){
		
	  return new Function(){
			@Override
			public void f(){
				
				if($(this).is(":invalid")){
					$(this).next(".errorLabel").show();
					$(this).addClass("inputError");
				} else{
					$(this).next(".errorLabel").hide();
				}
			}
		};
		
	}	
		


}
