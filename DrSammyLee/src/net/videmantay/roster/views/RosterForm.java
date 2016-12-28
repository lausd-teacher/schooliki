package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.$;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialInput;
import net.videmantay.roster.json.RosterJson;


public class RosterForm extends Composite{

	private static RosterFormUiBinder uiBinder = GWT.create(RosterFormUiBinder.class);

	interface RosterFormUiBinder extends UiBinder<Widget, RosterForm> {
	}
	
	
	@UiField
	MaterialCard card;
	
	@UiField
	MaterialInput title;
	
	@UiField
	MaterialInput description;
	
	@UiField
	MaterialInput roomNum;
	
	@UiField
	MaterialDatePicker startDate;
	
	@UiField
	MaterialDatePicker endDate;
	
	
	@UiField
	MaterialAnchorButton submitBtn;
	
	@UiField
	MaterialAnchorButton cancelBtn;
	
	@UiField
	HTMLPanel formContainer;
	
	
	private RosterJson data = JavaScriptObject.createObject().cast();

	public RosterForm() {
		initWidget(uiBinder.createAndBindUi(this));
		formContainer.getElement().setId("rosterForm");
	}
	
	@Override
	public void onLoad(){
		$("#rosterForm input").blur(getValidationFunction());
		
		startDate.addCloseHandler(new CloseHandler<MaterialDatePicker>(){
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
				// TODO Auto-generated method stub
				if(endDate.getDate() != null && startDate.getDate() != null){
					 
					 Date endDateValue = endDate.getValue();
					 if(endDateValue.before(startDate.getDate())){
						 $("#errorStartDateLabel").show();
						
					 }
				 }
					
			}
		});
		
		endDate.addCloseHandler(new CloseHandler<MaterialDatePicker>(){
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
			    if(startDate.getDate() != null && endDate.getDate() != null){
					 Date endDateValue = endDate.getValue();
					 if(endDateValue.before(startDate.getDate())){
						 $("#errorEndDateLabel").show();
						
					 }else{
						 $("#errorEndDateLabel").hide();
						 $("#errorStartDateLabel").hide();
						
					 }
				 }else if(endDate.getDate() != null){
					 $("#errorEndDateLabel").show();
				 }
			}
		});
		
		
		
		
		$(".errorLabel").hide();
		$("#errorEndDateLabel").hide();
		$("#errorStartDateLabel").hide();
		
	}
	
	//Need to find a better way to do this
	public RosterJson collectFormData(){
		
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		
		String json = "{"
				+ "\"title\":"+"\""+title.getText()+"\","
				+ "\"description\":"+"\""+description.getText()+"\","
				+ "\"roomNum\":"+"\""+roomNum.getText()+"\","
				+ "\"startDate\":"+"\""+df.format(startDate.getDate())+"\","
				+ "\"endDate\":"+"\""+df.format(endDate.getDate())+"\""
				+ "}";
		
		data = JsonUtils.safeEval(json);
		
		return data.cast();
		
	}
	
	private  Function getValidationFunction(){		
		return new Function(){
			@Override
			public void f(){
				GWT.log("event" + $(this).id());
				if($(this).is(":invalid")){
					$(this).next(".errorLabel").show();
					$(this).addClass("inputError");
					submitBtn.setEnabled(false);
				} else{
					$(this).next(".errorLabel").hide();
					submitBtn.setEnabled(true);
				}
			}
		};
		
	}

	public MaterialAnchorButton getSubmitBtn() {
		return this.submitBtn;
	}

	
	public MaterialAnchorButton getCancelBtn() {
		return this.cancelBtn;
	}


	public interface Presenter{
		
		public void submitButtonClick();
		public void cancelButtonClick();
		
	}

	



}
