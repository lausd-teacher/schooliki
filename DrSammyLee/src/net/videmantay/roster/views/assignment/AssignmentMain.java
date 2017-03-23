package net.videmantay.roster.views.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

import gwt.material.design.client.ui.MaterialAnchorButton;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.GradedWorkJson;

public class AssignmentMain extends Composite {

	private static AssignmentMainUiBinder uiBinder = GWT.create(AssignmentMainUiBinder.class);

	interface AssignmentMainUiBinder extends UiBinder<Widget, AssignmentMain> {
	}
	
	private final RosterUtils utils;
	@UiField
	HTMLPanel assignmentsTabContent;
	
	@UiField(provided=true)
	GradedWorkForm gradedworkForm ;
	
	@UiField
	MaterialAnchorButton fabBtn;
	private ClickHandler fabHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			gradedworkForm.show();
			
		}};
	
	
	AsyncDataProvider<GradedWorkJson> providesKey = new AsyncDataProvider<GradedWorkJson>(){

		@Override
		protected void onRangeChanged(HasData<GradedWorkJson> display) {
			// TODO Auto-generated method stub
			
		}};
		
	@UiField(provided=true)
	public final AssignmentGrid assignmentGrid = new AssignmentGrid(providesKey);

	public AssignmentMain(RosterUtils ru) {
		utils =ru;
		gradedworkForm = new GradedWorkForm(utils);
		initWidget(uiBinder.createAndBindUi(this));
		fabBtn.addClickHandler(fabHandler);
		
	}

}
