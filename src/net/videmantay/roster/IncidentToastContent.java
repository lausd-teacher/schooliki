package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.media.client.Audio;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.student.json.RosterStudentJson;

public class IncidentToastContent extends Composite {

	private static IncidentToastContentUiBinder uiBinder = GWT.create(IncidentToastContentUiBinder.class);

	interface IncidentToastContentUiBinder extends UiBinder<Widget, IncidentToastContent> {
	}

	public IncidentToastContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField 
	AudioElement audio;
	
	@UiField
	MaterialImage image;
	
	@UiField
	MaterialLabel summary;
	
	
	
	public void init(final IncidentReportJson report, StudentActionModal.ActionType type){
		String msg = "" ;
		
		switch(type){
		case SINGLE: RosterStudentJson student = report.getStudent().get(0); msg = student.getFirstName() + " " + student.getLastName();break;
		case MULTI:msg = report.getStudent().length() + " students ";break;
		case WHOLE:msg = "The Class ";break;
		}
		
		msg += " earned " + report.getIncident().getValue() + " for " + report.getIncident().getName();
		summary.setText(msg);
		
		if(report.getIncident().getValue() < 0){
			//sad owl audio
			audio.setSrc("");
			//set sad owl image
			image.setUrl("");
		}else{
			//happy owl audio
			audio.setSrc("");
			image.setUrl("");
		}
		
		
	}
	
	public void playAudio(){
		audio.load();
		audio.play();
	}

}
