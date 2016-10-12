package net.videmantay.roster;

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

import net.videmantay.roster.json.GradedWorkJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.url.RosterUrl;
import net.videmantay.student.json.RosterDetailJson;

public class RosterDisplay extends Composite{

	private static RosterDisplayUiBinder uiBinder = GWT.create(RosterDisplayUiBinder.class);

	@UiField
	RosterGrid rosterGrid;
	
	@UiField
	RosterForm rosterForm;
	
	@UiField
	MaterialButton	fab;
	
	ClickHandler fabClick = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
		
		$(fab).hide();
		$(rosterGrid).hide();
		$(rosterForm).show();
	
			
		}};
	Function rosterRedraw = new Function(){
		@Override
		public boolean f(Event e, Object...o){
			e.stopPropagation();
			e.preventDefault();
			$(rosterForm).hide();
			getRostersDataAndDrawGrid();
			$(rosterGrid).show();
			$(fab).show();
			
			return true;
		}
	};

	
	interface RosterDisplayUiBinder extends UiBinder<Widget, RosterDisplay> {
	}

	public RosterDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
		this.setSize("100%", "100%");
	}
	
	@Override
	public void onLoad(){
		
		getRostersDataAndDrawGrid();
		$(rosterForm).hide();
		fab.addClickHandler(fabClick);
		//handle the form and grid events here
		$(body).on("rosterredraw", rosterRedraw);
		
	}
	
	@Override 
	public void onUnload(){
		//clear up all the events on the bus with off
	}
	
	
	public void getRostersDataAndDrawGrid(){
		
		Ajax.get("/roster")
		.done(new Function(){
			@Override
			public void f(){
				JsArray<RosterJson> rosterList = JsonUtils.safeEval(this.arguments(0).toString()).cast();
				rosterGrid.drawGrid(rosterList);
			}
		}).progress(new Function(){
			@Override
			public void f(){
				
			 }
			
		});
		
		
	}

}
