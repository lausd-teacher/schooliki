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
	
	
	public static final String ABSENT_HTML_SYMBOL = "&#9747;";
	public static final String PRESENT_HTML_SYMBOL = "&#9731;";
	

		public RosterStudentCard(RosterStudentJson student) {
		initWidget(uiBinder.createAndBindUi(this));
		attendenceBadge.setVisibility(Visibility.HIDDEN);
		attendenceBadge.getElement().getStyle().setBackgroundColor("red");
		attendenceBadge.getElement().addClassName("attendenceBadge");
		attendenceBadge.setText(ABSENT_HTML_SYMBOL);
	}

	
	public void setData(RosterStudentJson student){
		this.getElement().setId(student.getId());
		studentName.setText(student.getFirstName() + " " + student.getLastName());
		String url= student.getImageUrl();
		studentImg.getStyle().setBackgroundImage("url('" + url +"')");
		studentName.getElement().setAttribute("style", "max-width:40px");
		
	}
	
	public MaterialBadge getPointsBadge() {
		return this.pointsBadge;
	}


	public MaterialBadge getAttendenceBadge() {
		return this.attendenceBadge;
	}

}
