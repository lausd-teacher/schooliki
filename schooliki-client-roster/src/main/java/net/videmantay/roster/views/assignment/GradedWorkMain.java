package net.videmantay.roster.views.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import static com.google.gwt.query.client.GQuery.*;
import net.videmantay.shared.url.RosterUrl;

import java.util.ArrayList;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.html.Div;
import net.videmantay.roster.json.GradedWorkJson;

public class GradedWorkMain extends Composite {

	private static GradedWorkMainUiBinder uiBinder = GWT.create(GradedWorkMainUiBinder.class);

	interface GradedWorkMainUiBinder extends UiBinder<Widget, GradedWorkMain> {
	}
	
	private final SimplePager pager = new SimplePager(){};
	private final GradedWorkForm form;
	
	@UiField
	HTMLPanel wrapper;
	
	@UiField
	Div gradedWorkViz;
	
	@UiField
	Div gradedWorkPager;
	
	@UiField
	MaterialAnchorButton fab;
	
	public GradedWorkMain(GradedWorkForm gradedWorkForm) {
		initWidget(uiBinder.createAndBindUi(this));
		this.form = gradedWorkForm;
		
		gradedWorkPager.add(pager);
		wrapper.add(form);
		
		//do fab
		fab.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				form.show();
				
			}});
	}//end const
	
	
	public void setAssignementGrid(AssignmentGrid assignmentGrid){
		pager.setDisplay(assignmentGrid);
		gradedWorkViz.clear();
		gradedWorkViz.add(assignmentGrid);
		assignmentGrid.setSize("100%","100%");
		assignmentGrid.setRowCount(0);
	}
	
	@Override
	public void onLoad(){
		gradedWorkViz.setSize("100%", "35em");
		
		$(body).on("", new Function(){
			@Override
			public boolean f(Event e, Object...o){
				
				
				return true;
			}
		});
	}

}
