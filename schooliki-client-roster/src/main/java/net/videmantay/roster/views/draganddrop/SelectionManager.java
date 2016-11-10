package net.videmantay.roster.views.draganddrop;

import com.google.gwt.dom.client.DivElement;

public class SelectionManager {
	
	
	static DivElement currentlySelected = null;
	static DivElement previouslySelected = null;
	
	
	public static void setCurrentlySelected(DivElement selected){
		previouslySelected = currentlySelected;
		currentlySelected = selected;
	}
	
	public static boolean isSelectionActive(){
		return currentlySelected == null ? true : false;
	}
	
	public static DivElement getSelection(){
		return currentlySelected;
	}

}
