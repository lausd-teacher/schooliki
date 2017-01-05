package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.*;
import com.google.gwt.query.client.plugins.ajax.Ajax;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.RosterJson;

public class RosterDisplay extends Composite{

	private static RosterDisplayUiBinder uiBinder = GWT.create(RosterDisplayUiBinder.class);



	@UiField
	RosterGrid rosterGrid;
	
	@UiField
	RosterForm rosterForm;
	
	@UiField
	MaterialButton	fab;
	
	@UiField
	MaterialModal deleteRosterModal;
	
	@UiField
	MaterialAnchorButton cancelBtnDeleteRoster;
	
	@UiField
	MaterialAnchorButton okBtnDeleteRoster;
/// Create all click handlers ////////////////////////////
	private ClickHandler showFormHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			showForm();
			
		}};
		
		private ClickHandler submitBtnClick = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				MaterialLoader.showLoading(true, rosterGrid);
				Promise promise = rosterForm.submit();	
				promise.done(new Function(){
					@Override
					public void f(){
						console.log("INFO: done promised is called ");
						MaterialLoader.showLoading(false, rosterGrid);
						rosterForm.setVisible(false);
						rosterGrid.setVisible(true);
						fab.setVisible(true);
						console.log(this.arguments(0));
						RosterJson roster = ((RosterJson)this.arguments(0)).cast();
						console.log("INFO: roster back from server is " + roster.toString());
						rosterGrid.addRoster(roster);
					}
				});
				rosterForm.reset();
			}};
			
		private ClickHandler cancelBtnClick = new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					rosterForm.setVisible(false);
					rosterGrid.setVisible(true);
					fab.setVisible(true);
					rosterForm.cancel();
				}};
	private ClickHandler cancelDelete = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			//hide the modal
			deleteRosterModal.closeModal();
		}
	};
	private ClickHandler okDelete = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			e.stopPropagation();
			deleteRosterModal.closeModal();
		RosterJson ros = $(deleteRosterModal).data("roster", RosterJson.class);
		JsArray<RosterJson> newList = JsArray.createArray().cast();
		for(int i = 0; i < RosterUtils.getRosterList().length(); i++){
			if(ros == RosterUtils.getRosterList().get(i)){
				continue;
			}//end if
			newList.push(RosterUtils.getRosterList().get(i));
		}//end for
		RosterUtils.setRosterList(newList);
		//redraw the gird
		rosterGrid.clear();
		for(int i = 0; i <RosterUtils.getRosterList().length(); i++){
			rosterGrid.addRoster(RosterUtils.getRosterList().get(i));
		}
		Ajax.ajax(Ajax.createSettings().setData(ros.getId())
				.setDataType("json")
				.setContentType("application/json")
				.setType("DELETE")
				.setUrl(RosterUrl.roster(ros.getId()))
				).done(new Function(){ 
					@Override
					public void f(){MaterialToast.fireToast("Roster successfully deleted");}
				});
		
		
		}
		
	};
		
///////////////////////end click handlers ///////////////////////////////////
//helper function for triggers
	Function promptDelete = new Function(){
		@Override
		public boolean f(Event e, Object...objects){
			$(deleteRosterModal).data("roster", (RosterJson)objects[0]);
			deleteRosterModal.openModal();
			return true;
		}
	};
	Function updateRosterForm = new Function(){
		@Override
		public boolean f(Event e, Object...objects){
			showForm();
			rosterForm.setData((RosterJson)objects[0]);
			return true;
		}
	};
				
	interface RosterDisplayUiBinder extends UiBinder<Widget, RosterDisplay> {
	}

	public RosterDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
		this.setSize("100%", "100%");
		rosterForm.setVisible(false);
		//add clic to fab
		fab.addClickHandler(showFormHandler);
		
		//add click to form
		rosterForm.submitBtn.addClickHandler(submitBtnClick);
		rosterForm.cancelBtn.addClickHandler(cancelBtnClick);
		okBtnDeleteRoster.addClickHandler(okDelete);
		cancelBtnDeleteRoster.addClickHandler(cancelDelete);
		
		//react to triggers
		$(body).on("updateRosterForm", updateRosterForm);
		
		$(body).on("promptDelete", promptDelete);
	}
	
	@Override
	public void onLoad(){
		MaterialLoader.showLoading(true, rosterGrid);
		//ajax to get roster list and populate the grid
		Ajax.get(RosterUrl.roster()).done(new Function(){
			@Override
			public void f(){
		MaterialLoader.showLoading(false,rosterGrid);	
		JsArray<RosterJson> rosters = JsonUtils.safeEval((String)this.getArgument(0)).cast();
		if(rosters == null || rosters.length() < 1){
			rosterGrid.showEmptyList();
		}else{
			//set the roster list in rosterUtils
			RosterUtils.setRosterList(rosters);
			
			//cycle through and draw
			for(int i = 0; i < rosters.length(); i++){
			rosterGrid.addRoster(rosters.get(i));
			}//end for 
		}
			}
		});
		
	}//end on load
	
	private void showForm(){
		rosterForm.setVisible(true);
		rosterGrid.setVisible(false);
		fab.setVisible(false);
	}
		
}
