package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.$;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.roster.json.AppUserJson;
import net.videmantay.roster.views.draganddrop.SelectionManager;
import net.videmantay.student.json.RosterStudentJson;

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
	
	final RosterStudentJson currentStudent;
	
	final Long currentRosterId; 
		
	public static final String ABSENT_HTML_SYMBOL = "&#9747;";
	public static final String PRESENT_HTML_SYMBOL = "&#9731;";
	

	public RosterStudentCard(RosterStudentJson student) {
		initWidget(uiBinder.createAndBindUi(this));
		attendenceBadge.setVisibility(Visibility.HIDDEN);
		attendenceBadge.getElement().getStyle().setBackgroundColor("red");
		attendenceBadge.getElement().addClassName("attendenceBadge");
		attendenceBadge.setText(ABSENT_HTML_SYMBOL);
		
		pointsBadge.getElement().getStyle().setBackgroundColor("green");
		pointsBadge.setText(student.getIncidentPointsAggregate()+"");
		pointsBadge.getElement().addClassName("pointsBadge");
		
		this.studentActionModal = factory.getStudentActionModal();
		currentStudent = student;
		rosterStudentPanel.getElement().addClassName("rosterStudentPanel");
		setData(currentStudent);
		this.currentRosterId = factory.getCurrentRoster().getId();
		this.clientFactory = factory;
		//depends on what we load from the server
		rosterStudentPanel.getElement().getStyle().setOpacity(0.5);
	
	}

	
	public void setData(AppUserJson student){
		this.getElement().setId(student.getId());
		studentName.setText(student.getName());
		String url= student.getImageUrl();
		studentImg.getStyle().setBackgroundImage("url('" + url +"')");
		studentName.getElement().setAttribute("style", "max-width:40px");
		setupPanelForGrid();
	}
	
	public MaterialBadge getPointsBadge() {
		return this.pointsBadge;
	}


	public MaterialBadge getAttendenceBadge() {
		return this.attendenceBadge;
	}
	
	private void setupPanelForGrid(){
		rosterStudentPanel.addDomHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				SelectionManager.unselectCurrentlySelectedStudent();
				SelectionManager.selectStudent(rosterStudentPanel);
				if(!clientFactory.isRollMode()){
				     studentActionModal.show();
				}else{
					NativeEvent nEvent = event.getNativeEvent();
					EventTarget eTarget = nEvent.getCurrentEventTarget();
					Element elem = eTarget.cast();
					Element attendenceBadge = $(elem).find(".attendenceBadge").get(0);
					toggleAttendenceBadge(attendenceBadge);
				}

			}
		}, ClickEvent.getType());
		studentImg.getStyle().setHeight(70, Unit.PX);
		studentImg.getStyle().setWidth(70, Unit.PX);
		//We are not doing anything right now, probably will be adding some event here
	}
	
	
	private void toggleAttendenceBadge(Element attendenceBadge){
		
		if(attendenceBadge.getStyle().getBackgroundColor().equals("red")){
			attendenceBadge.getStyle().setBackgroundColor("green");
			rosterStudentPanel.getElement().getStyle().setOpacity(1);
			attendenceBadge.setInnerHTML(PRESENT_HTML_SYMBOL);
		}else{
			attendenceBadge.getStyle().setBackgroundColor("red");
			attendenceBadge.setInnerHTML(ABSENT_HTML_SYMBOL);
			rosterStudentPanel.getElement().getStyle().setOpacity(0.5);
		}
		
		
	}
		
	public interface Presenter{
		public void RosterStudentClickEvent();
	}
	
	
//	Ajax.get("/roster/"+currentRosterId+"/student/"+currentStudent.getId()+"/incident").done(new Function() {
//	@Override
//	public void f() {
//		JsArray<IncidentJson> incidents = JsonUtils.safeEval(arguments(0).toString());
//		studentActionModal.getIncidentsContainer().clear();
//		  for(int i = 0; i <incidents.length(); i++){
//			  IncidentJson incident = incidents.get(i);
//			  IncidentTypeJson type = clientFactory.findIncidentTypeById(incident.getIncidentTypeId());
//			  StudentIncidentCard card = new StudentIncidentCard(currentStudent.getName(), type.getImageUrl() ,incident.getName());
//			  studentActionModal.getIncidentsContainer().add(card);
//			  
//		  }
//		studentActionModal.show();
//			}	
//}).progress(new Function() {
//	@Override
//	public void f() {
//
//	}
//
//}).fail(new Function() {
//	@Override
//	public void f() {
//		Window.alert("Could not load student incidents");
//	}
//});
	
	

}
