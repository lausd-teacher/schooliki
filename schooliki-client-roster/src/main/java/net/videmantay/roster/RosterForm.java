package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import elemental.json.impl.JsonUtil;
import gwt.material.design.client.constants.ShowOn;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialLoader;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.LoginInfo;
import net.videmantay.shared.url.RosterUrl;
import net.videmantay.student.json.TeacherInfoJson;

import static com.google.gwt.query.client.GQuery.*;

import java.text.ParseException;
import java.util.Date;

import com.google.common.base.Preconditions;
import com.google.gwt.query.client.*;
import com.google.gwt.query.client.plugins.ajax.Ajax;


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
	
	//private final RosterForm $this;
	
	private RosterJson data = JavaScriptObject.createObject().cast();

	public RosterForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//$this = this;
		formContainer.getElement().setId("rosterForm");

		
		
		
		submitBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				submit();
				
				
			}});
		cancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				card.setVisible(false);
				
			}});
		
		
	}
	
	@Override
	public void onLoad(){
		$("#rosterForm input").blur(getValidationFunction());
		
		
		//Special event for handling dates
		
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
	
	public void edit(RosterJson r){
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		
		this.data = Preconditions.checkNotNull(r);
		title.setValue(data.getTitle());
		description.setValue(data.getDescription());
		roomNum.setValue(data.getRoomNum());
		
		
		
		startDate.setDate(df.parse(data.getStartDate()));
		endDate.setDate(df.parse(data.getEndDate()));	
		

	}
	
	public boolean hasErrors(){
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		try {
			data.setStartDate(df.format(startDate.getDate()));
			data.setEndDate(df.format(endDate.getDate()));
			data.setTitle(title.getValueOrThrow());
		} catch (ParseException e) {
			return true;
		}
		data.setDescription(description.getValue());
		data.setRoomNum(roomNum.getValue());
	
		
		return false;
		
	}
	
	public void submit(){
		if(hasErrors()){
			//show the errors
			return;
		}
		collectDataForm();
			
		console.log(collectDataForm());
		
		
		
	
		GQuery.ajax("/roster", Ajax.createSettings().setData(data).setType("POST").setDataType("json"))
		.done(new Function(){
			@Override
			public void f(){
				$(body).trigger("rosterredraw");
				MaterialLoader.showLoading(false);
				
			}
		}).progress(
				new Function(){
					@Override
					public void f(){
						MaterialLoader.showLoading(true);
					}
		}).fail(
					new Function(){
						@Override
						public void f(){
						   Window.alert("Error Please try again later");
						}
		 });		
	}
	
	
	//Need to find a better way to do this
	private String collectDataForm(){
		
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		
		String json = "{"
				+ "\"title\":"+"\""+title.getText()+"\","
				+ "\"description\":"+"\""+description.getText()+"\","
				+ "\"roomNum\":"+"\""+roomNum.getText()+"\","
				+ "\"startDate\":"+"\""+df.format(startDate.getDate())+"\","
				+ "\"endDate\":"+"\""+df.format(endDate.getDate())+"\""
				+ "}";
		
		data = JsonUtils.safeEval(json);
		
		return json;
		
	}
	
	private  Function getValidationFunction(){		
		return new Function(){
			@Override
			public void f(){
				GWT.log("event" + $(this).id());
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
