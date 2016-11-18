package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialLabel;
import gwtquery.plugins.ui.interactions.Draggable;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.json.AppUserJson;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.views.RosterDashboardPanel.View;
import net.videmantay.roster.views.components.StudentIncidentCard;
import net.videmantay.roster.views.draganddrop.SelectionManager;

public class RosterStudentPanel extends Composite {

	private static RosterStudentPanelUiBinder uiBinder = GWT.create(RosterStudentPanelUiBinder.class);

	interface RosterStudentPanelUiBinder extends UiBinder<Widget, RosterStudentPanel> {
	}
	
	@UiField
	MaterialCard rosterStudentPanel;
	
	@UiField
	DivElement studentImg;
	
	@UiField
	MaterialLabel studentName;
	
	@UiField
	MaterialBadge pointsBadge;
	
	@UiField
	MaterialBadge attendenceBadge;
	
	final StudentActionModal studentActionModal;
	
	final AppUserJson currentStudent;
	
	final Long currentRosterId; 
		
	final ClientFactory clientFactory;
	

	public RosterStudentPanel(AppUserJson student, View viewType, ClientFactory factory) {
		initWidget(uiBinder.createAndBindUi(this));
		attendenceBadge.setVisible(false);
		pointsBadge.getElement().getStyle().setBackgroundColor("green");
		pointsBadge.setText("x");
		
		this.studentActionModal = factory.getStudentActionModal();
		currentStudent = student;
		rosterStudentPanel.getElement().addClassName("rosterStudentPanel");
		setData(currentStudent, viewType);
		this.currentRosterId = factory.getCurrentRoster().getId();
		this.clientFactory = factory;
	
	}

	
	public void setData(AppUserJson student, View viewType){
		
		
		this.getElement().setId(student.getId());
		studentName.setText(student.getName());
		String url= student.getImageUrl();
		studentImg.getStyle().setBackgroundImage("url('" + url +"')");
		studentName.getElement().setAttribute("style", "max-width:40px");
		if(viewType.equals(View.GRID)){
			setupPanelForGrid();
		}else{
			setupPanelForSeatingChart();
		}
		
		
	}
	
	
	private void setupPanelForSeatingChart(){
		studentImg.getStyle().setHeight(40, Unit.PX);
		studentImg.getStyle().setWidth(40, Unit.PX);
		studentImg.addClassName("studentDraggable");
		studentImg.getStyle().setPosition(Position.ABSOLUTE);
		//studentImg.setDraggable("false");
		rosterStudentPanel.getElement().getStyle().setWidth(60, Unit.PX);
		rosterStudentPanel.getElement().getStyle().setHeight(60, Unit.PX);
		//rosterStudentPanel.
		studentName.getElement().getStyle().setPaddingTop(40, Unit.PX);
		studentName.getElement().getStyle().setFontSize(12, Unit.PX);
		setUpDragAndDrop();
		
	}
	
	private void setupPanelForGrid(){
		rosterStudentPanel.addDomHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				SelectionManager.unselectCurrentlySelectedStudent();
				SelectionManager.selectStudent(rosterStudentPanel);
				studentActionModal.show();
				
				
				
//				Ajax.get("/roster/"+currentRosterId+"/student/"+currentStudent.getId()+"/incident").done(new Function() {
//					@Override
//					public void f() {
//						JsArray<IncidentJson> incidents = JsonUtils.safeEval(arguments(0).toString());
//						studentActionModal.getIncidentsContainer().clear();
//						  for(int i = 0; i <incidents.length(); i++){
//							  IncidentJson incident = incidents.get(i);
//							  IncidentTypeJson type = clientFactory.findIncidentTypeById(incident.getIncidentTypeId());
//							  StudentIncidentCard card = new StudentIncidentCard(currentStudent.getName(), type.getImageUrl() ,incident.getName());
//							  studentActionModal.getIncidentsContainer().add(card);
//							  
//						  }
//						studentActionModal.show();
//							}	
//				}).progress(new Function() {
//					@Override
//					public void f() {
//
//					}
//
//				}).fail(new Function() {
//					@Override
//					public void f() {
//						Window.alert("Could not load student incidents");
//					}
//				});
				
				
				
				
			}
		}, ClickEvent.getType());
		studentImg.getStyle().setHeight(70, Unit.PX);
		studentImg.getStyle().setWidth(70, Unit.PX);
		//We are not doing anything right now, probably will be adding some event here
	}
	
	private void setUpDragAndDrop(){
		Draggable.Options options = Draggable.Options.create();
		options.revert("invalid");
		options.helper("clone");
		
		$(studentImg).as(Ui).draggable(options);
	}
	
	public void disableDragAndDrop(){
		Draggable.Options options = Draggable.Options.create();
		options.disabled();
		$(studentImg).as(Ui).draggable(options);
	}
	
	public interface Presenter{
		public void RosterStudentClickEvent();
	}
	
	

}
