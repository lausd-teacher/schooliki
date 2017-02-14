package net.videmantay.roster;

import com.google.gwt.user.client.ui.IsWidget;

public interface HasClassroomDashboardView extends IsWidget {

	public void cancel(String state);
	
	public void checkHW();
	
	public void deselectAll();
	
	public void doneCheckHW();
	
	public void doneGroups();
		
	public void doneMultipleSelect();
	
	public void donePickRandom();
	
	public void doneSelectAll();
	
	public void doneTakeRoll();
	
	public void drawGrid();
	
	public void edit();
	
	public void doneEditing();
	
	public void groups();
	
	public void home();
	
	public boolean isEditing();
	
	public void multipleSelect();
	
	public void pickRandom();
	
	public void selectAll();
	
	public void takeRoll();
	
	public void undo();
	
	public void unHome();
	
	public State getState();
	
	public void setState(State state);
	//enum for state
	public enum State{DASHBOARD,ROLL, HW,GROUP, MULTIPLE_SELECT,RANDOM, FURNITURE_EDIT, STUDENT_EDIT, STATION_EDIT};
}
