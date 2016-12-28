package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialLabel;
import gwtquery.plugins.ui.interactions.Draggable;
import net.videmantay.roster.json.AppUserJson;
import net.videmantay.roster.views.RosterDashboardPanel.View;

public class RosterStudentPanel extends Composite {

	private static RosterStudentPanelUiBinder uiBinder = GWT.create(RosterStudentPanelUiBinder.class);

	interface RosterStudentPanelUiBinder extends UiBinder<Widget, RosterStudentPanel> {
	}
	
	@UiField
	HTMLPanel rosterStudentPanel;
	
	@UiField
	DivElement studentImg;
	
	@UiField
	MaterialLabel studentName;

	public RosterStudentPanel(AppUserJson student) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(student);
	}
	
	private void setupPanelForSeatingChart(){
		studentImg.getStyle().setHeight(40, Unit.PX);
		studentImg.getStyle().setWidth(40, Unit.PX);
		studentImg.addClassName("studentDraggable");
		studentImg.getStyle().setPosition(Position.ABSOLUTE);
		rosterStudentPanel.getElement().getStyle().setWidth(60, Unit.PX);
		rosterStudentPanel.getElement().getStyle().setHeight(60, Unit.PX);
		rosterStudentPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		studentName.getElement().getStyle().setPaddingTop(40, Unit.PX);
		studentName.getElement().getStyle().setFontSize(12, Unit.PX);
		setUpDragAndDrop();
	}
	
	public void setData(AppUserJson student){
		this.getElement().setId(student.getId());
		studentImg.setAttribute("studentId", student.getId());
		studentName.setText(student.getName());
		String url= student.getImageUrl();
		studentImg.getStyle().setBackgroundImage("url('" + url +"')");
		studentName.getElement().setAttribute("style", "max-width:40px");
		setupPanelForSeatingChart();
	}
	
	
	private void setUpDragAndDrop(){
		Draggable.Options options = Draggable.Options.create();
		options.revert("invalid");
		options.helper("clone");
		$(studentImg).as(Ui).draggable(options);
	}
	


}
