package net.videmantay.roster;

import com.google.gwt.user.client.ui.IsWidget;

public interface HasRosterDashboardView extends IsWidget {

	public void checkHW();
	
	public void groups();
	
	public void takeRoll();
	
	public void pickRandom();
	
	public void selectAll();
	
	public void multipleSelect();
	
	public void home();
	
	public void arrangeFurniture();
	
	public void arrangeStudents();
	
	public void manageStations();
	
	public void doneCheckHW();
	
	public void doneGroups();
	
	public void doneTakeRoll();
	
	public void donePickRandom();
	
	public void doneSelectAll();
	
	public void undo();
	
	public void cancel(String state);
	
	public void deselectAll();
	
	public void doneMultipleSelect();
	
	public void doneArrangeFurniture();
	
	public void doneArrangeStudents();
	
	public void doneManageStations();
	
	
}
