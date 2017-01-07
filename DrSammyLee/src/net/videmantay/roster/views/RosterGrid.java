package net.videmantay.roster.views;

import com.google.gwt.user.client.Timer;
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
	
	MaterialRow row ;
	final RosterGrid grid = this;
	
	RosterGrid(){
		this.setWidth("100%");
		this.setHeight("100%");
	}
	
	public void showEmptyList(){
		this.clear();
		this.add(this.emptyList);
	}
	
	public void showErrorMessage(){
		this.clear();
		this.add(this.errorMessage);
	}
	
	public void addRoster(final RosterJson roster){
		new Timer(){

			@Override
			public void run() {
				if(row == null){
					row = new MaterialRow();
					grid.add(row);
				}
				if(row.getWidgetCount() == 3){
					row = new MaterialRow();
					grid.add(row);
				}
				MaterialColumn col = new MaterialColumn();
				col.setGrid("s12 m4 l4");
				RosterPanel panel = new RosterPanel();
				
				panel.setColor(roster.getColor());
				panel.setData(roster);
				col.add(panel);
				row.add(col);
				
			}}.schedule(100);
		
	}

	

}
