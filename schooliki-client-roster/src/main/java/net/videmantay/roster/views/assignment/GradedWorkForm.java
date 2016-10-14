package net.videmantay.roster.views.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import java.util.Date;

import com.google.gwt.query.client.Function;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import net.videmantay.shared.url.*;
import net.videmantay.roster.json.GradedWorkJson;
import net.videmantay.roster.json.RosterJson;

public class GradedWorkForm extends Composite {

	private static GradedWorkFormUiBinder uiBinder = GWT.create(GradedWorkFormUiBinder.class);
	private final GradedWorkForm $this = this;
	private JsArrayNumber studentList = JsArrayNumber.createArray().cast();
	private GradedWorkJson data;
	private String url = "";
	
	@UiField
	MaterialModal modal;
	
	@UiField
	FormPanel form;
	
	@UiField
	MaterialInput  title;
	
	@UiField
	MaterialTextArea description;
	
	@UiField
	MaterialListBox lang;
	
	@UiField
	MaterialListBox subject;
	
	@UiField
	MaterialListBox type;
	
	@UiField
	MaterialDatePicker dueDate;
	
	@UiField
	MaterialDatePicker assignedDate;
	
	@UiField
	MaterialDoubleBox pointsPossible;
	
	@UiField
	MaterialCheckBox selectAllBox;
	
	@UiField
	MaterialRow assignToGrid;
	
	@UiField
	MaterialButton okBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	
	interface GradedWorkFormUiBinder extends UiBinder<Widget, GradedWorkForm> {
	}

	public GradedWorkForm() {
		
		initWidget(uiBinder.createAndBindUi(this));
		form.getElement().setId("assignmentForm");
		
		okBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$this.submit();
				
			}});
		
		cancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$this.hide();
			}});
		
//	RosterJson roster = (RosterJson) window.getPropertyJSO("roster").cast();
//		for(int i = 0; i <roster.getRosterStudents().length(); i++){
//			AssignToGridItem item = new AssignToGridItem();
//			item.setStudent(roster.getRosterStudents().get(i));
//			MaterialColumn col = new MaterialColumn();
//			col.setGrid("s2");
//			col.add(item);
//			assignToGrid.add(col);
//			assignToGrid.setVisible(false);
//		}
		selectAllBox.addValueChangeHandler(new ValueChangeHandler<Boolean>(){

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(event.getValue()){
					assignToGrid.setVisible(false);
				}else{
					assignToGrid.setVisible(true);
				}	
			}});
	
	}//end constructor
	
	public void show(){
		modal.openModal();
	}
	
	public void hide(){
		form.reset();
		data = null;
		$(".item-student", assignToGrid).removeClass("student-selected", "student-unselected");
		assignToGrid.setVisible(false);
		modal.closeModal();
	}
	public void reset(){
		form.reset();
		data = GradedWorkJson.createObject().cast();
	}
	
	public void submit(){
		data = GradedWorkJson.createObject().cast();
		
		data.setTitle(title.getValue());
		;
		data.setDescription(description.getValue());
	
		DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
		data.setAssignedDate(dtf.format(assignedDate.getValue()));
		data.setDueDate(dtf.format(dueDate.getValue()));
		
		data.setAssignedTo(studentList);
		data.setMediaUrl(url);
		RosterJson roster = window.getPropertyJSO("roster").cast();
		data.setRosterId(roster.getId());
		
		if(selectAllBox.getValue()){
			for(int i = 0; i <roster.getRosterStudents().length(); i++){
				studentList.push(roster.getRosterStudents().get(i).getId());
			}
		}
		MaterialLoader.showLoading(true);
		Ajax.post(RosterUrl.CREATE_ASSIGNMENT, $$("assignment:" + JsonUtils.stringify(data)))
		.done(new Function(){
			@Override
			public void f(){
				GradedWorkJson assignment = JsonUtils.safeEval((String)this.arguments(0)).cast();
				$(body).trigger("redrawAssignmentGrid", assignment );	
			}//end f
		});//end done
		//hide form even  before ajax is back
		this.hide();
	}//end submit
	
	@Override
	public void onLoad(){
		$(".item-student",assignToGrid).click(new Function(){
			@Override
			public boolean f(Event e){
				if($(this).hasClass("student-selected")){
					$(this).removeClass("student-selected").addClass("student-unselected");
				}else{
					if($(this).hasClass("student-unselected")){
						$(this).removeClass("student-unselected");
					}
					$(this).addClass("student-selected");
				}
				
				return true;
			}
		});
		
$("#assignmentForm input").blur(getValidationFunction());
		
		
		//Special event for handling dates
		
   assignedDate.addCloseHandler(new CloseHandler<MaterialDatePicker>(){
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
				// TODO Auto-generated method stub
				if(dueDate.getDate() != null && assignedDate.getDate() != null){
					 
					 Date endDateValue = dueDate.getValue();
					 if(endDateValue.before(assignedDate.getDate())){
						 $("#errorAssignedDateLabel").show();
						 return;
					 }
				 }
				
				
				
			    	
			}
		});
		
      dueDate.addCloseHandler(new CloseHandler<MaterialDatePicker>(){
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
			    if(assignedDate.getDate() != null && dueDate.getDate() != null){
					 Date endDateValue = dueDate.getValue();
					 if(endDateValue.before(assignedDate.getDate())){
						 $("#errorDueDateLabel").show();
					 }else{
						 $("#errorDueDateLabel").hide();
						 $("#errorAssignedDateLabel").hide();
					 }
				 }else if(dueDate.getDate() != null){
					 $("#errorDueDateLabel").show();
				 }
			}
		});
		
		
		
		
		$(".errorLabel").hide();
		$("#errorDueDateLabel").hide();
		$("#errorAssignedDateLabel").hide();
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
