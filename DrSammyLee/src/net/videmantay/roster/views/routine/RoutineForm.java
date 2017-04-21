package net.videmantay.roster.views.routine;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;
import static gwtquery.plugins.ui.Ui.Ui;

import java.util.ArrayList;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwtquery.plugins.ui.DraggableUi;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.RotatableUi;
import gwtquery.plugins.ui.interactions.CursorAt;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;
import gwtquery.plugins.ui.interactions.Rotatable;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.routine.json.FullRoutineJson;
import net.videmantay.roster.routine.json.FurnitureJson;
import net.videmantay.roster.routine.json.RoutineJson;
import net.videmantay.roster.routine.json.SeatingChartJson;
import net.videmantay.roster.routine.json.StudentSeatJson;
import net.videmantay.roster.views.RosterStudentPanel;
import net.videmantay.shared.Action;
import gwt.material.design.addins.client.richeditor.MaterialRichEditor;
import gwt.material.design.addins.client.richeditor.base.constants.ToolbarButton;

public class RoutineForm extends Composite {

	private static RoutineFormUiBinder uiBinder = GWT.create(RoutineFormUiBinder.class);

	interface RoutineFormUiBinder extends UiBinder<Widget, RoutineForm> {
	}
	
	
	@UiField
	public MaterialButton submitBtn;
	
	@UiField
	public MaterialButton cancelBtn;
		
	@UiField
	public MaterialTextBox title;
	
	@UiField
	public MaterialTextArea description;
	
	@UiField
	public MaterialCheckBox  isDefault;
	
	@UiField
	public ProcedureForm procForm;
			
	private  FullRoutineJson originalData;
	private SeatingChartJson seatingChart;
	private  final RosterUtils utils;
	
	public RoutineForm(RosterUtils ru){
		this.utils = ru;
		initWidget(uiBinder.createAndBindUi(this));
	}
	public void setData(FullRoutineJson frj) {
		if(frj == null){
			frj = FullRoutineJson.create();
			frj.getRoutine().setRosterId(utils.getCurrentRoster().getId());
		}
		originalData = frj;
		title.setText(originalData.getRoutine().getTitle());
		description.setText(originalData.getRoutine().getDescript());
		if(frj.getRoutineConfig().getProcedures() != null && frj.getRoutineConfig().getProcedures().length() < 0){
			for(int i = 0; i <frj.getRoutineConfig().getProcedures().length(); i++){
				procForm.addProcItem(frj.getRoutineConfig().getProcedures().get(i).getProcedure());
			}//end for
		}else{
				procForm.addProcItem(null);
			}
		
		console.log("Routine Form constructor");
	
	}
	
	public void setSeatingChart(SeatingChartJson scj){
		this.seatingChart =scj;
	} 
	
	public SeatingChartJson getSeatingChart(){
		return this.seatingChart;
	}
	
	public RoutineJson getData(){
		RoutineJson formData = JavaScriptObject.createObject().cast();
		
		formData.setDescript(description.getText());
		formData.setTitle(title.getText());
		formData.setIsDefault(isDefault.getValue());
		
		return formData;
	}
	

	public MaterialTextBox getClassTimeTitle() {
		return this.title;
	}


	public MaterialTextArea getDescription() {
		return this.description;
	}
	
	public void cancel(){
		
	}
	
	public void submit(){
		
	}

	private void clear(){
		
	}

	
}
