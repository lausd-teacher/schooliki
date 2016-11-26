package net.videmantay.roster.views.classtime;


import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

import java.util.List;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;


import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.views.components.FurniturePanelItem;
import net.videmantay.roster.views.draganddrop.DragAndDropManager;
import net.videmantay.roster.views.draganddrop.FurnitureAddAction;
import net.videmantay.roster.views.draganddrop.SelectionManager;
import net.videmantay.roster.views.draganddrop.UndoRedoManager;
import net.videmantay.shared.Action;

public class SeatingChartPanel extends Composite implements HasRosterDashboardView {

	private static SeatingChartPanelUiBinder uiBinder = GWT.create(SeatingChartPanelUiBinder.class);

	interface SeatingChartPanelUiBinder extends UiBinder<Widget, SeatingChartPanel> {
	}

	
	@UiField
	HTMLPanel furniturePanel;
	
	@UiField
	DivElement floorPlan;
	
	@UiField
	HTMLPanel studentsPanel;

	@UiField
    MaterialButton removeButton; 
	
	@UiField
	HTMLPanel seatingChart;
	
	@UiField
	DivElement editingDiv;

	
	
	ClientFactory factory;
	
	UndoRedoManager undoRedoManager;
	
	
	
	public SeatingChartPanel(ClientFactory factory) {
		initWidget(uiBinder.createAndBindUi(this));
        this.factory = factory;
        editingDiv.setId("editing");
        floorPlan.setId("flrPlan");
        undoRedoManager = factory.getUndoRedoManager();
        
        enableDragAndDrop();
        
        List<FurniturePanelItem> furnitureList = DragAndDropManager.getFurnitureItems();
        
        MaterialRow row = new MaterialRow();
         for(int i = 0; i < furnitureList.size(); i++ ){
        	
        	 MaterialColumn column = new MaterialColumn();
        	 column.add(furnitureList.get(i));
        	 row.add(column);
        	 
         }
         
         furniturePanel.add(row);
       

	}
	
	
	public void enableDragAndDrop(){
		 Droppable.Options options = Droppable.Options.create();
	        
	        options.accept(".furnitureItem");
	        
	        $(floorPlan).as(Ui).droppable(options).on("drop", new Function(){
				@Override
				public boolean f(Event e, Object...o){
					DroppableUi ui =(DroppableUi)o[0];
					Element droppedElement = ui.draggable().get();
					NativeEvent nativeEvent = e.cast();
					
					GWT.log("dropping here " + droppedElement.getClassName());
					
					GWT.log("received draggable : " + droppedElement.getParentElement().getClassName());
					
					DivElement eventTarget = nativeEvent.getEventTarget().cast();
					
					GWT.log("target : " + eventTarget.getClassName());
					
					 if(factory.isEditMode()){
						 Droppable.Options dropOptions = Droppable.Options.create();
							dropOptions.accept(".studentDraggable");
						  if(eventTarget.getClassName().contains("floorPlan") &&  !droppedElement.getParentElement().getClassName().contains("floorPlan")){
							  Draggable.Options dragOptions = Draggable.Options.create();
								dragOptions.containment("parent");
								
							 DivElement adjustedDroppedElement =  $(ui.helper()).clone().as(Ui).draggable(dragOptions).droppable(dropOptions)
							  
							  .rotatable().css("height", "60px").css("width", "100px").appendTo($(floorPlan).as(Ui)).on("focus click", new Function(){
								  public boolean f(Event e, Object...o){
									 
									if(factory.isEditMode()){
									  e.stopPropagation();
									  NativeEvent nativeEvent = e.cast();
									  DivElement eventTarget = nativeEvent.getEventTarget().cast();
									  if(eventTarget.getClassName().contains("furnitureItem")){
									        SelectionManager.setCurrentlySelected(eventTarget);
									  }
									}
									  
									  return true;
								  }
							  }).blur(new Function(){
								  public boolean f(Event e, Object...o){
									  NativeEvent nativeEvent = e.cast();
									  Element eventTarget = nativeEvent.getRelatedEventTarget().cast();
									  GWT.log("blurring" + eventTarget);
									 SelectionManager.unSelect(eventTarget);
									  
									  return true;
								  }
							  }).get().getItem(1).cast();
							 
							
							  
							 
							  
							 // DivElement dElement = droppedElement.cast();
						
							  undoRedoManager.recordAction(new FurnitureAddAction(adjustedDroppedElement));
							  
						  }else if(eventTarget.getClassName().contains("furnitureItem") && droppedElement.getParentElement().getClassName().contains("rosterStudentPanel")){
							  Draggable.Options dragOptions = Draggable.Options.create();
								dragOptions.helper("original");
								dragOptions.containment("parent");
								//
							 $(ui.helper()).clone().css("left", "0").css("top", "0").as(Ui).draggable(dragOptions).appendTo($(eventTarget).as(Ui));
							 String studentId = droppedElement.getAttribute("studentid");
							 $("#"+studentId+" .rosterPanelSeatedDiv").css("visibility", "visible");
							  
						  }else{
							  if(droppedElement.getClassName().contains("furnitureItem")){
								  droppedElement.getStyle().setWidth(100, Unit.PX);
								  droppedElement.getStyle().setHeight(60, Unit.PX);
							  }else if(droppedElement.getClassName().contains("studentDraggable")){
								  droppedElement.getStyle().setWidth(40, Unit.PX);
								  droppedElement.getStyle().setHeight(40, Unit.PX);
							  }
							  GWT.log("case 3" + droppedElement.getClassName());
						  }
					 }else{
						MaterialToast.fireToast("Please activate editing before dropping anything on the class plan", 2000); 
					 }
					
					return true;	
				   }
				});
		
	}
	
	

	@Override
	public void checkHW() {
		
		
	}

	@Override
	public void groups() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeRoll() {
	
	}

	@Override
	public void home() {
		
	}
	
	@Override
	public void unHome(){

	}

	@Override
	public void pickRandom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneCheckHW() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneGroups() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneTakeRoll() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void donePickRandom() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void multipleSelect(){
		
	}
	@Override
	public void doneMultipleSelect(){
		
	}
	@Override
	public void doneSelectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}
	
	public  void arrangeStudents(){

	};
	
	public void doneArrangeStudents(){
		
	};
	
	
	
	public  void arrangeFurniture(){
	
		
	}
	
	
	
	

	public void deleteFurniture(final GQuery $this){
		
	}

	@Override
	public void manageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneManageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel(final String state) {
		
	}
	

	@Override
	public void doneArrangeFurniture() {
		// TODO Auto-generated method stub
		
	}


	public HTMLPanel getFurniturePanel() {
		return this.furniturePanel;
	}


	public DivElement getFloorPlan() {
		return this.floorPlan;
	}


	public HTMLPanel getStudentsPanel() {
		return this.studentsPanel;
	}
	

	public MaterialButton getRemoveButton() {
		return this.removeButton;
	}


	public DivElement getEditingDiv() {
		return this.editingDiv;
	}


	public interface Presenter{
		void removeSeatingChartActionButtonClickEvent();
		void editingDivClickEvent();
	}
	


}
