package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.*;
import com.google.gwt.query.client.plugins.ajax.Ajax;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.RosterJson;

public class RosterDisplay extends Composite{

	private static RosterDisplayUiBinder uiBinder = GWT.create(RosterDisplayUiBinder.class);

	private final RosterUtils utils;

	@UiField
	MaterialContainer rosterContainer;
	
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
				MaterialLoader.showLoading(true);
				Promise promise = rosterForm.submit();	
				promise.done(new Function(){
					@Override
					public void f(){
						console.log("argument recieved by AJAX: ");
						console.log(this.arguments(0));
						final RosterJson retrieved = this.arguments(0);
					
						new Timer(){
							
							@Override
							public void run() {
								console.log("INFO: done promised is called ");
								MaterialLoader.showLoading(false);
								rosterForm.setVisible(false);
								rosterContainer.setVisible(true);
								fab.setVisible(true);
								
								
								boolean noMatch = true;
								//stupid longs i  hate you
								String curId = "" + retrieved.getId();
								
								for(int i = 0; i <utils.getRosterList().length(); i++){
									String comId = ""+ utils.getRosterList().get(i).getId();
									if(curId.equals(comId)){
										RosterJson updateMe = utils.getRosterList().get(i);
										updateMe.setColor(retrieved.getColor());
										updateMe.setTitle(retrieved.getTitle());
										updateMe.setDescription(retrieved.getDescription());
										updateMe.setEndDate(retrieved.getEndDate());
										updateMe.setStartDate(retrieved.getStartDate());
										updateMe.setRoomNum(retrieved.getRoomNum());
										noMatch = false;
										console.log("There was a match for roster we updated");
										break;
									}
								}//end for we cycled through it all
								if(noMatch){
									console.log("There was no match for roster it is a new one");
									utils.getRosterList().push(retrieved);
								}
								console.log("Here is the rostser form - no Match is " + noMatch);
								console.log("The array is");
								console.log(utils.getRosterList());
								
								drawGrid();
							}
								
						}.schedule(1000);
					}	
				});
				rosterForm.reset();
			}
		};
			
		private ClickHandler cancelBtnClick = new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					fab.setVisible(true);
					rosterForm.reset();
				rosterContainer.setVisible(true);
					rosterForm.setVisible(false);
				}};
	private ClickHandler cancelDelete = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			//hide the modal
			deleteRosterModal.closeModal();
			$("div.lean-overlay").remove();
		}
	};
	private ClickHandler okDelete = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			e.stopPropagation();
			deleteRosterModal.closeModal();
		RosterJson ros = $(deleteRosterModal).data("roster", RosterJson.class);
		JsArray<RosterJson> newList = JsArray.createArray().cast();
		
		//make longs string pain!!!
		String curId = ros.getId()+"";
		for(int i = 0; i < utils.getRosterList().length(); i++){
			String comId = utils.getRosterList().get(i).getId() + "";	
			if(curId.equals(comId)){
				continue;
			}//end if
			newList.push(utils.getRosterList().get(i));
		}//end for
		utils.setRosterList(newList);
		//redraw the gird
		drawGrid();
		Ajax.ajax(Ajax.createSettings().setData(ros.getId())
				.setDataType("json")
				.setContentType("application/json")
				.setType("DELETE")
				.setUrl(RosterUrl.roster(ros.getId()))
				).done(new Function(){ 
					@Override
					public void  f(){

						MaterialToast.fireToast("Roster successfully deleted");
					$("div.lean-overlay").remove();
		
					}
				});
		
		
		}
		
	};
		
///////////////////////end click handlers ///////////////////////////////////
//helper function for triggers
	Function promptDelete = new Function(){
		@Override
		public boolean f(Event e, Object...objects){
			e.stopPropagation();
			$(deleteRosterModal).data("roster", (RosterJson)objects[0]);
			deleteRosterModal.openModal();
			return true;
		}
	};
	Function updateRosterForm = new Function(){
		@Override
		public boolean f(Event e, Object...objects){
			e.stopPropagation();
			showForm();
			rosterForm.setData((RosterJson)objects[0]);
			return true;
		}
	};
				
	interface RosterDisplayUiBinder extends UiBinder<Widget, RosterDisplay> {
	}

	public RosterDisplay(RosterUtils ru) {
		this.utils = ru;
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
		MaterialLoader.showLoading(true, rosterContainer);
		new Timer(){

			@Override
			public void run() {
				MaterialLoader.showLoading(false, rosterContainer);
				drawGrid();
			}}.schedule(250);
		// do in const
	}
		
	private void showForm(){
		rosterForm.setVisible(true);
		rosterContainer.setVisible(false);
		fab.setVisible(false);
	}
	private final void drawGrid(){
		console.log("Draw Grid: called");
		rosterContainer.add(addRosters(utils.getRosterList()));
		
	}
	
	private final MaterialRow addRosters(JsArray<RosterJson> rosters){
		final MaterialRow row = new MaterialRow();
		
		for(int i =0; i< rosters.length();i++){
			if(rosters.get(i) == null){continue;}
			MaterialColumn col = new MaterialColumn();
			col.setGrid("s12 m4 l4");
			RosterPanel panel = new RosterPanel();
			
			panel.setColor(rosters.get(i).getColor());
			panel.setData(rosters.get(i));
			col.add(panel);
			row.add(col);
			console.log("roster grid added :");
			console.log(rosters.get(i));
		}
		
		return row;
	}
		
}
