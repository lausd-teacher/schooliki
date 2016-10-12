package net.videmantay.roster;

import com.google.gwt.user.client.ui.HTMLPanel;
import java.util.ArrayList;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.json.RosterJson;


public class RosterGrid extends MaterialContainer{

	final HTMLPanel emptyList = new HTMLPanel("<h5 class='emptyRosterListHeading'>Your Roster List Is Empty </h5>"+
												"<h6 class='emptyRosterListContent'>you can begin pressing the big  plus button at" +
												"the botton of the screen.</h6>");
	final HTMLPanel errorMessage = new HTMLPanel("<h5 class='emptyRosterListHeading'>Error getting the list of rosters, please check your connnection or try again later </h5>"+
			"<h6 class='emptyRosterListContent'>you can begin pressing the big  plus button at" +
			"the botton of the screen.</h6>");
	
	@Override
	public void onLoad(){
		
		Ajax.get("/roster")
		.done(new Function(){
			@Override
			public void f(){
                   
				console.log(arguments(0).toString());
				JsArray<RosterJson> rosterList = JsonUtils.safeEval(this.arguments(0).toString()).cast();
				drawGrid(rosterList);
				MaterialLoader.showLoading(false);
			}
		}).progress(new Function(){
			@Override
			public void f(){
				MaterialLoader.showLoading(true);
			 }
			
		}).fail(new Function(){
			@Override
			public void f(){
				MaterialLoader.showLoading(false);
				showErrorMessage();
			 }
		});
		
		
	}
	
	public void drawGrid(JsArray<RosterJson> rosterList){
		
		if(rosterList == null || rosterList.length() <= 0){
			showEmptyList();
		}else{
		this.clear();
		MaterialRow row = new MaterialRow();
		for(int i= 0; i< rosterList.length(); i++){
			MaterialColumn col = new MaterialColumn(12,6,4);
			RosterPanel panel = new RosterPanel();
			panel.setData(rosterList.get(i));
			col.add(panel);
			row.add(col);
			this.add(row);
			}
		}
	}
	
	public void showEmptyList(){
		this.clear();
		this.add(this.emptyList);
	}
	
	public void showErrorMessage(){
		this.clear();
		this.add(this.errorMessage);
	}



}
