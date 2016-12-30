package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLoader;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;

import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.GradedWorkJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterDetailJson;

public class RosterDisplay extends Composite{

	private static RosterDisplayUiBinder uiBinder = GWT.create(RosterDisplayUiBinder.class);



	@UiField
	RosterGrid rosterGrid;
	
	@UiField
	RosterForm rosterForm;
	
	@UiField
	MaterialButton	fab;
	

	interface RosterDisplayUiBinder extends UiBinder<Widget, RosterDisplay> {
	}

	public RosterDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
		this.setSize("100%", "100%");
		rosterForm.setVisible(false);
	}
	
	
	public RosterGrid getRosterGrid() {
		return this.rosterGrid;
	}

	public RosterForm getRosterForm() {
		return this.rosterForm;
	}

	public MaterialButton getFab() {
		return this.fab;
	}
		
	public interface Presenter{
		
		public void addRosterClickEvent();
		
		
		
	}

}
