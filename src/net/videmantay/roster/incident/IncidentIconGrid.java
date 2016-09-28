package net.videmantay.roster.incident;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;

public class IncidentIconGrid  extends Composite{

	
	private String[] iconList = {"doctor", "happyGirl", "rocket","happyBoy","warningCone","warningSign","scientist","pharmasist"};
	
	public  MaterialRow grid = new MaterialRow();
	private Function clickFunction = new Function(){
		@Override
		public boolean f(Event e){
			e.stopPropagation();
			e.preventDefault();
			
			
			GQuery el = $(e.getEventTarget());
		if(el.prop("tagName").equals("use")){
			el = $(el).closest("svg");
		}
			
			GQuery iconPanel = $("div#iconFormIconPanel > div.gwt-HTML");
			iconPanel.children().remove();
			iconPanel.append(el.clone());
			iconPanel.data("icon", el.id());
			return true;
		}
	};
	
	public IncidentIconGrid(){
		this.initWidget(grid);
		grid.setBackgroundColor("blue-grey darken-1");
		com.google.gwt.dom.client.Style gridStyle = grid.getElement().getStyle();
		gridStyle.setBorderColor("Dim");
		gridStyle.setBorderStyle(Style.BorderStyle.DOTTED);
		gridStyle.setBorderWidth(0.1, Unit.EM);
		gridStyle.setMarginTop(1, Unit.EM);
		
		for(String s: iconList){
			MaterialColumn c = new MaterialColumn();
			c.setGrid("s6 m4 l2");
			String html = "<svg viewBox='0 0 150 200' class='incidentIcon' style='width:7em; height:8em' id='"
					+s+"'>"
					+"<use  xmlns:xlink='http://www.w3.org/1999/xlink' xlink:href='../img/allIcons.svg#" 
					+ s
					+"' /></svg>";
			HTMLPanel icon = new HTMLPanel(html);
			c.add(icon);
			grid.add(c);
		}
	}
	
	@Override
	public void onLoad(){
		$(this).click(clickFunction);
	}
	
	
	
}
