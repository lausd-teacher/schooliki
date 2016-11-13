package net.videmantay.roster.views.draganddrop;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;

import gwt.material.design.client.ui.MaterialCard;

public class SelectionManager {
	
	
	static DivElement currentlySelected = null;
	static DivElement previouslySelected = null;
	
	static MaterialCard incidentCard = null;
	
	
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
		
		//GWT.log("unselected " + parent.getClassName());
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
		    incidentCard.getElement().addClassName("selectedIncidentCard");
		
	}
	
	public static void unSelectCurrentSelectedIncidentCard(){
		if(incidentCard != null){
			incidentCard.getElement().removeClassName("selectedIncidentCard");
		    incidentCard = null;
		}
}
	
	
	

}
