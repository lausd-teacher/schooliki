package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.*;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialInput;
import net.videmantay.roster.RosterUrl;
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
	
	@UiField
	FormPanel form;

	private RosterJson data = JavaScriptObject.createObject().cast();

	public RosterForm() {
		initWidget(uiBinder.createAndBindUi(this));
		formContainer.getElement().setId("rosterForm");
	}
	
	@Override
	public void onLoad(){
		$("#rosterForm input").blur(validate());
		
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
	public RosterJson formData(){
		
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		
		data.setDescription(description.getValue());
		data.setTitle(title.getValue());
		data.setEndDate(df.format(endDate.getValue()));
		data.setStartDate(df.format(startDate.getValue()));
		data.setRoomNum(roomNum.getValue());
		
		
		
		return data;
		
	}
	
	private  Function validate(){		
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


	public Promise submit(){
		console.log("SUBMIT: roster hit the form data is : " + JsonUtils.stringify(formData()));
		if(submitBtn.isEnabled()){
			return Ajax.ajax(Ajax.createSettings()
								.setContentType("application/json")
								.setType("post")
								.setDataType("json")
								.setData(formData())
								.setUrl(RosterUrl.roster()));
		}else{
			return null;
		}
	}
	
	public void cancel(){
		data = null;
		form.reset();
	}
	
	public void reset(){
		form.reset();
	}
	
	public void setData(RosterJson newData){
		if(newData != null){
			data = newData;
			
			title.setValue(data.getTitle());
			description.setValue(data.getDescription());
			startDate.setValue(new Date(Date.parse(data.getStartDate())));
			endDate.setValue(new Date(Date.parse(data.getEndDate())));
			roomNum.setValue(data.getRoomNum());
		}
	}

}
