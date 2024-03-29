package net.videmantay.roster.views.student;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollection;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

public class RosterStudentCollection extends Composite {

	private static RosterStudentCollectionUiBinder uiBinder = GWT.create(RosterStudentCollectionUiBinder.class);

	interface RosterStudentCollectionUiBinder extends UiBinder<Widget, RosterStudentCollection> {
	}

	@UiField
	HTMLPanel emptyStudentList;
	
	@UiField
	MaterialCollection studentCollection;
	
	private final RosterJson roster = window.getPropertyJSO("roster").cast();
	
	
	public RosterStudentCollection() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void showEmpty(){
		$(emptyStudentList).show();
		$(studentCollection).hide();
	}
	
	@Override
	public void onLoad(){
		
	}
	
	public void drawList(){
		studentCollection.clear();
		console.log("Draw chart called");

				$(emptyStudentList).hide();
				$(studentCollection).show();
	}

}
