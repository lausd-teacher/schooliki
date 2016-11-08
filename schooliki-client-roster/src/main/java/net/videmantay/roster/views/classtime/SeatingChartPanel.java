package net.videmantay.roster.views.classtime;


import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

import java.util.List;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.interactions.Droppable;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.views.components.FurniturePanelItem;
import net.videmantay.roster.views.draganddrop.DragAndDropManager;
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
	

	private final Stack<Action> stack = new Stack<Action>();
	
	ClientFactory factory;
	
	
	
	public SeatingChartPanel(ClientFactory factory) {
		initWidget(uiBinder.createAndBindUi(this));
        this.factory = factory;
        Droppable.Options options = Droppable.Options.create();
        
        options.accept(".furnitureItem");
        
        $(floorPlan).as(Ui).droppable(options).on("drop", new Function(){
			@Override
			public boolean f(Event e, Object...o){
				
				DroppableUi ui =(DroppableUi)o[0];
				Element droppedElement = ui.draggable().get();
				
				
				GWT.log("dropping here " + droppedElement.getClassName());
				
				if(droppedElement.getClassName().contains("studentDraggable")){
					GWT.log("received draggable");
					
				}
				
				return true;	
			   }
			});
        
        
        
        List<FurniturePanelItem> furnitureList = DragAndDropManager.getFurnitureItems();
        
        MaterialRow row = new MaterialRow();
         for(int i = 0; i < furnitureList.size(); i++ ){
        	
        	 MaterialColumn column = new MaterialColumn();
        	 column.add(furnitureList.get(i));
        	 row.add(column);
        	 
         }
         
         furniturePanel.add(row);
       

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
	


}
