package net.videmantay.roster.views.draganddrop;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.ui.MaterialCard;

public class SelectionManager {
	
	static DivElement currentlySelected = null;
	static DivElement previouslySelected = null;
	
	static MaterialCard incidentCard = null;
	
	static MaterialCard selectedStudent = null;
	
	static ImageElement selectedIncidentType = null;
	
	
	public static void setCurrentlySelected(DivElement selected){
		previouslySelected = currentlySelected;
		currentlySelected = selected;
		currentlySelected.getStyle().setProperty("border", "solid 5px green");
	}
	
	public static boolean isSelectionActive(){
		return currentlySelected == null ? false : true;
	}
	
	public static DivElement getSelection(){
		return currentlySelected;
	}
	
	public static void unSelect(Element parent){
	 if(isElementNull(parent)){
		  currentlySelected.getStyle().setProperty("border", "");
	      previouslySelected = currentlySelected;
	      currentlySelected = null;
	 }else{
		 if(!parent.getClassName().contains("floorPlanActionButton")){
			  currentlySelected.getStyle().setProperty("border", "");
		      previouslySelected = currentlySelected;
		      currentlySelected = null;
			}
	 }
	}
	
	
	public static native boolean isElementNull(Element element )/*-{
		if(!element)
		   return true;
		 
		 return false;
		
	}-*/;
	
	
	
	/* Each click event should deselect the selected element except for an action button and the element itself */
	public static native boolean registerDocumentClickEvent()/*-{
	
	   $wnd.document.addEventListener("click", function(event){
	   	      if(@net.videmantay.roster.views.draganddrop.SelectionManager::currentlySelected){
	   	          @net.videmantay.roster.views.draganddrop.SelectionManager::previouslySelected = @net.videmantay.roster.views.draganddrop.SelectionManager::currentlySelected;
	   	          @net.videmantay.roster.views.draganddrop.SelectionManager::currentlySelected.style.border = "";
	   	          @net.videmantay.roster.views.draganddrop.SelectionManager::currentlySelected = null; 
	   	      }
	   	});
	
}-*/;
	
	
	public static void selectIncidentCard(MaterialCard card){
		    incidentCard = card;
		    incidentCard.getElement().addClassName("selected");
	}
	public static void unSelectCurrentSelectedIncidentCard(){
		if(incidentCard != null){
			incidentCard.getElement().removeClassName("selected");
		    incidentCard = null;
		}
    }
	public static MaterialCard getSelectedIncidentCard(){
		return incidentCard;
	}
	public static void selectStudent(MaterialCard studentPanel){
		selectedStudent = studentPanel;
		selectedStudent.getElement().addClassName("selected");
	}
	public static void unselectCurrentlySelectedStudent(){
		if(selectedStudent != null){
			selectedStudent.getElement().removeClassName("selected");
			selectedStudent = null;
		}
	}
	
	public static MaterialCard getSelectedStudentCard(){
		return selectedStudent;
	}
	
	public static void selectIncidentType(ImageElement incidentType){
		selectedIncidentType = incidentType;
	}
	
	public static void unselectCurrentlySelectedIncidentType(){
		if(selectedIncidentType != null){
			selectedIncidentType = null;
		}
	}
	
}
