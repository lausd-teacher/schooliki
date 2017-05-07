package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.*;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import net.videmantay.roster.HasClassroomDashboardView;
import net.videmantay.roster.RosterUtils;
import net.videmantay.student.json.StudentAttendanceJson;

public class ClassroomDashboardPanel extends Composite
{

	private static ClassroomDashboardPanelUiBinder uiBinder = GWT.create(ClassroomDashboardPanelUiBinder.class);

	interface ClassroomDashboardPanelUiBinder extends UiBinder<Widget, ClassroomDashboardPanel> {
	}
	

	@UiField
	public MaterialSwitch gridSwitch;
	
	@UiField
	public MaterialIcon hwIcon;
	
	@UiField
	public MaterialIcon groupsIcon;
	
	@UiField
	public MaterialIcon rollIcon;
	
	@UiField
	public MaterialIcon multipleIcon;
	
	@UiField
	public MaterialIcon randomIcon;
	
	@UiField
	public MaterialIcon editIcon;
	
	@UiField
	public HTMLPanel tab1Main;
	
	@UiField
	public MaterialRow toolbar;
	
	@UiField
	public MaterialRow hwToolbar;
	/////////bar vs button essential the same job but bar 
	//is 100 across the screen and detached from nav panel
	@UiField
	public MaterialAnchorButton classroomtimeBar;
	@UiField
	public MaterialAnchorButton classtimeBtn;
	////////////////////////////////////////////////////////
	@UiField
	public MaterialDropDown classtimeDrop;
	
	@UiField
	public MaterialDropDown classtimeDrop2;
	
    @UiField
    public MaterialLink classDropDownManageLink;
	
	@UiField
	public MaterialAnchorButton doneBtn;
	
	@UiField
	public MaterialAnchorButton CancelBtn;
		
	@UiField
	public MaterialButton hwActivatorBtn;
	
	@UiField
	public MaterialRow rollToolbar;
	
	@UiField
	public MaterialAnchorButton saveRollBtn;
	
	@UiField
	public MaterialButton cancelRollBtn;
	
	@UiField
	public MaterialTab tab;
	
	@UiField
	public MaterialTabItem calTab;
	
	@UiField
	public MaterialTabItem reportsTab;
	
	@UiField
	public MaterialTabItem dashboardTab;
	
	@UiField
	public HTMLPanel calendarContainer;
	

	
	private HasClassroomDashboardView display;
	final RosterUtils utils;

		
	public ClassroomDashboardPanel(RosterUtils ru) {
		utils = ru;
		initWidget(uiBinder.createAndBindUi(this));
		hwToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		
		//add click to icons
		hwIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				showDoneBar();
				
			}});
		rollIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				showRollBar();
				display.unHome();
				display.takeRoll();
				
			}});
		
	}
	
	public void showDoneBar(){
		toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		hwToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
		
	}
	public void showToolBar(){
		hwToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		rollToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		toolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	
	public void showRollBar(){
		toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		rollToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}

	
	public void setDisplayInTab1(HasClassroomDashboardView display){
			tab1Main.clear();
			this.display = display;
			tab1Main.add(display);			
	}
	
	public HasClassroomDashboardView getDisplay(){
		return display;
	}

    public void clearDropDown(){
    	     classtimeDrop.clear();
    	     classtimeDrop.add(classDropDownManageLink);
    }
    
 
    @Override
    public void onLoad(){
 
    }//end onload

 
	
}
