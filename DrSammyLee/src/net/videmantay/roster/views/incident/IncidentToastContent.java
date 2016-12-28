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
			
		
		
	}

}
