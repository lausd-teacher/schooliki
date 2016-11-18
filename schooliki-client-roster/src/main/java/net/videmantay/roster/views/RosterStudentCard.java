package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialLabel;
import gwtquery.plugins.ui.interactions.Draggable;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.json.AppUserJson;
import net.videmantay.roster.views.RosterDashboardPanel.View;
import net.videmantay.roster.views.draganddrop.SelectionManager;

public class RosterStudentCard extends Composite {

	private static RosterStudentPanelUiBinder uiBinder = GWT.create(RosterStudentPanelUiBinder.class);

	interface RosterStudentPanelUiBinder extends UiBinder<Widget, RosterStudentCard> {
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
	

	public RosterStudentCard(AppUserJson student, ClientFactory factory) {
		initWidget(uiBinder.createAndBindUi(this));
		attendenceBadge.setVisible(false);
		attendenceBadge.getElement().addClassName("attendenceBadge");
		
		pointsBadge.getElement().getStyle().setBackgroundColor("green");
		pointsBadge.setText(student.getIncidentPointsAggregate()+"");
		pointsBadge.getElement().addClassName("pointsBadge");
		
		this.studentActionModal = factory.getStudentActionModal();
		currentStudent = student;
		rosterStudentPanel.getElement().addClassName("rosterStudentPanel");
		setData(currentStudent);
		this.currentRosterId = factory.getCurrentRoster().getId();
		this.clientFactory = factory;
	
	}

	
	public void setData(AppUserJson student){
		this.getElement().setId(student.getId());
		studentName.setText(student.getName());
		String url= student.getImageUrl();
		studentImg.getStyle().setBackgroundImage("url('" + url +"')");
		studentName.getElement().setAttribute("style", "max-width:40px");
		setupPanelForGrid();
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
		
	public interface Presenter{
		public void RosterStudentClickEvent();
	}
	
	

}
