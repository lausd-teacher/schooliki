package net.videmantay.roster.incident;

import java.util.List;

import com.google.appengine.labs.repackaged.com.google.common.collect.ImmutableList;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;

public class IncidentIconGrid  extends Composite implements ClickHandler{

	 HTMLPanel setIconPanel ;
	
	List<String> iconList = ImmutableList.<String>builder()
			.add("doctoer").add("scientist").add("pharmasist")
			.add("")
			.build();
	
	final MaterialRow grid = new MaterialRow();
	
	public IncidentIconGrid(){
		
	}
	
	@Override
	public void onClick(ClickEvent event) {
		event.stopPropagation();
		event.preventDefault();
		
		setIconPanel.clear();
		setIconPanel.getElement().setInnerHTML($(event).closest("svg").html());
		
	}
	
	public void init(HTMLPanel panel){
		this.setIconPanel = panel;
	
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
	

}
