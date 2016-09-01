package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.media.client.Audio;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.roster.json.IncidentReportJson;
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
	MaterialImage studentImage;
	
	@UiField
	HTMLPanel incidentImage;
	
	@UiField
	MaterialLabel summary;
	
	
	
	public void init(final IncidentReportJson report){
		String msg = "" ;
		
		switch(report.getActionType()){
		case "Single": RosterStudentJson student = report.getStudents().get(0);
				msg = student.getFirstName() + " " + student.getLastName();
				studentImage.setUrl(student.getThumbnails().get(2).getUrl());
				break;
		case "Multi":msg = report.getStudents().length() + " students ";
					studentImage.setUrl("toMultiStudentPic");
					break;
		case "Whole":msg = "The Class ";
					studentImage.setUrl("WholeClassPic");break;
		}
		
		msg += " earned " + report.getIncident().getValue() + " for " + report.getIncident().getName();
		summary.setText(msg);
		
		String html = "<svg class='incidentToastPic' viewBox='150 200'>"
				+ "<use xlink:href='/img/allIcons.svg#"
				+ report.getIncident().getIconUrl()
				+"/></svg>";
		
		if(report.getIncident().getValue() < 0){
			//sad owl audio
			audio.setSrc("/audio/badNews.mp3");
		}else{
			//happy owl audio
			audio.setSrc("/audio/HooHooHoo2.mp3");
			
		}
		
		
	}
	
	public void playAudio(){
		audio.load();
		audio.play();
	}

}
