package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.html.Span;
import gwtquery.plugins.ui.interactions.Draggable;
import net.videmantay.roster.json.AppUserJson;

public class RosterStudentPanel extends Composite {

	private static RosterStudentPanelUiBinder uiBinder = GWT.create(RosterStudentPanelUiBinder.class);

	interface RosterStudentPanelUiBinder extends UiBinder<Widget, RosterStudentPanel> {
	}
	
	@UiField
	HTMLPanel rosterStudentPanel;
	
	@UiField
	DivElement studentImg;
	
	@UiField
	Span name;
	


	public RosterStudentPanel(AppUserJson student) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(student);
	}

	
	public void setData(AppUserJson student){
		//set the id of the panel to student id 
		//this is so we can query and hide it when necessary
		this.getElement().setId(student.getId() +"");
		name.setText(student.getName());
		String url= student.getImageUrl();
		studentImg.getStyle().setBackgroundImage("url('" + url +"')");
		studentImg.addClassName("studentDraggable");
		studentImg.setDraggable("true");
		setUpDragAndDrop();
	}
	
	
	private void setUpDragAndDrop(){
		Draggable.Options options = Draggable.Options.create();
		options.revert("invalid");
		
		$(studentImg).as(Ui).draggable(options);
		
		
	}

}
