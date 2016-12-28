package net.videmantay.roster.views.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.html.Div;

public class GradedWorkMain extends Composite {

	private static GradedWorkMainUiBinder uiBinder = GWT.create(GradedWorkMainUiBinder.class);

	interface GradedWorkMainUiBinder extends UiBinder<Widget, GradedWorkMain> {
	}
	
	private final SimplePager pager = new SimplePager();
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
	}
	
	
	public void setAssignementGrid(AssignementTable table){
		//pager.setDisplay(assignmentGrid);
		gradedWorkViz.clear();
		gradedWorkViz.add(table);
		table.setSize("100%","100%");
		gradedWorkViz.setSize("100%", "35em");
	}
	
     public MaterialAnchorButton getFab() {
		return this.fab;
	}

	public GradedWorkForm getForm() {
		return this.form;
	}

	public interface Presenter{
    	 void assignementGridFabClick();
     }
}
