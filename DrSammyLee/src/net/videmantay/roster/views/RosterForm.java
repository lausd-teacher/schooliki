package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.*;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.RosterJson;


public class RosterForm extends Composite{

	private static RosterFormUiBinder uiBinder = GWT.create(RosterFormUiBinder.class);

	interface RosterFormUiBinder extends UiBinder<Widget, RosterForm> {
	}
	
	
	@UiField
	MaterialCard card;
	
	@UiField
	MaterialRow colorRow;
	
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
	
	GQuery $colorBtns;

	private RosterJson data = JavaScriptObject.createObject().cast();
	private String colorVal = "red darken-2";

	public RosterForm() {
		initWidget(uiBinder.createAndBindUi(this));
		formContainer.getElement().setId("rosterForm");
		//setup color row
		for(String s: RosterColors.colors){
			MaterialColumn col = new MaterialColumn();
			col = new MaterialColumn();
			col.setGrid("s1");
			col.setMargin(2);
			final MaterialButton button = new MaterialButton();
			$(button).addClass("colorBtn");
			button.setBackgroundColor(s);
			button.setSize("20px", "20px");
			button.setPadding(3);
			button.setMarginRight(3);
			button.setDataAttribute("buttonColor", s);
			$(button).data("buttonColor", s);
			if(s.equals("red darken-2")){
				$(button).css($$("border: 2px solid DimGray; padding: 1em"));
			}
			button.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent e){
					$(".colorBtn").css($$("padding:0em; border-width:0px;"));
					$(e.getSource()).css($$("padding:1em; border:2px solid DimGray"));
					colorVal = button.getBackgroundColor();
				}
			});
			col.add(button);
			colorRow.add(col);
		}
	}
	
	@Override
	public void onLoad(){
		$("#rosterForm input").blur(validate());
		//init colorBtns
		$colorBtns = $("button.colorBtn");
		
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
		data.setColor(colorVal);
		

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
	
	
	public void reset(){
		
		form.reset();
		data = JavaScriptObject.createObject().cast();
		colorVal = "red darken-2";
		$colorBtns.css($$("padding:0em; border-width:0px;"));
		$colorBtns.filter("[data-buttoncolor='red darken-2']").css($$("padding:1em; border:2px solid DimGray"));
	}
	
	public void setData(RosterJson newData){
		if(newData != null){
			data = newData;
			
			title.setValue(data.getTitle());
			description.setValue(data.getDescription());
			startDate.setValue(new Date(Date.parse(data.getStartDate())));
			endDate.setValue(new Date(Date.parse(data.getEndDate())));
			roomNum.setValue(data.getRoomNum());
			$colorBtns.css($$("padding:0em; border-width:0px;"));
			$colorBtns.filter("[data-buttoncolor='"+data.getColor()+"']").css($$("padding:1em; border:2px solid DimGray"));
					
		}
	}

}
