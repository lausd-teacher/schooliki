package net.videmantay.roster.views.incident;

import com.google.common.primitives.Longs;
import com.google.common.base.Splitter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import java.util.List;
import java.util.ArrayList;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.roster.json.IncidentReportJson;
import net.videmantay.roster.json.RosterJson;
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
	
	private final RosterJson roster = window.getPropertyJSO("roster").cast();
	
	
	public void init( IncidentReportJson report){
			
		String msg = "" ;
		console.log(report.getActionType());
		
		ArrayList<RosterStudentJson> students = new ArrayList<RosterStudentJson>();
		List<String >stuIds = Splitter.on(',').splitToList(report.getStudentIds());
		for(int i = 0; i < roster.getRosterStudents().length(); i++){
			for(int j = 0; j < stuIds.size(); j++){
				if(roster.getRosterStudents().get(i).getId().equals(stuIds.get(j))){
					students.add(roster.getRosterStudents().get(i));
				}
			}
			
		}
		switch(report.getActionType()){
		case "Single": RosterStudentJson student = students.get(0);
				
				break;
		case "Multi":msg = students.size() + " students ";
					studentImage.setUrl("toMultiStudentPic");
					break;
		case "Whole":msg = "The Class ";
					studentImage.setUrl("WholeClassPic");break;
		}
		
		msg += "<br/>earned&nbsp;" + report.getIncident().getValue() + "&nbsp;for<br/>" + report.getIncident().getName();
		summary.setText(msg);
		
		String html = "<svg class='incidentToastPic' viewBox='0 0 150 200' style='width:150px;height:200px'>"
				+ "<use xlink:href='/img/allIcons.svg#"
				+"'/></svg>";
		incidentImage.add(new HTML(html));
		
		if(report.getIncident().getValue() < 0){
			//sad owl audio
			audio.setSrc("/audio/badNews.mp3");
		}else{
			//happy owl audio
			audio.setSrc("/audio/HooHooHoo2.mp3");
			
		}
		//audio.setAutoplay(true);
		
	}

}
