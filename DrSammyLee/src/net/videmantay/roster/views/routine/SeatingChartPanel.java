package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.plugins.effects.PropertiesAnimation.EasingCurve;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.events.ClearActiveEvent;
import gwt.material.design.client.events.ClearActiveEvent.ClearActiveHandler;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.html.Div;
import gwtquery.plugins.ui.DraggableUi;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.RotatableUi;
import gwtquery.plugins.ui.interactions.CursorAt;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;
import gwtquery.plugins.ui.interactions.Rotatable;
import gwtquery.plugins.ui.utilities.BoundingBox;

import java.util.ArrayList;
import java.util.Stack;

import net.videmantay.roster.HasClassroomDashboardView;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.routine.json.FurnitureJson;
import net.videmantay.roster.routine.json.SeatingChartJson;
import net.videmantay.roster.routine.json.StudentSeatJson;
import net.videmantay.roster.views.RosterStudentPanel;
import net.videmantay.shared.Action;

public class SeatingChartPanel extends Composite implements HasClassroomDashboardView {

	

 private static SeatingChartPanelUiBinder uiBinder = GWT.create(SeatingChartPanelUiBinder.class);
	 

	interface SeatingChartPanelUiBinder extends UiBinder<Widget, SeatingChartPanel> {
	}

	private SeatingChartJson data;
	private SeatingChartJson oldData = SeatingChartJson.createObject().cast();
	private boolean isEditing = false;
	//use arrayList to make editing easier;
	private ArrayList<FurnitureJson> tempFurnitureList;
	//save draggableParent for refernce
	private GQuery $dragParent;
	
	State state = State.DASHBOARD;
	
	@UiField
	DivElement floorPlan;
	
	@UiField
	MaterialCollection studentList;
	
	@UiField
	HTMLPanel seatingChart;
	
	@UiField
	MaterialRow seatingChartToolbar;
	
	@UiField
	MaterialButton stateSelectBtn;
	
	@UiField
	MaterialDropDown scDropDown;
	
	@UiField
	DivElement homeTools;
	
	@UiField
	DivElement editTools;
	
	@UiField
	MaterialCollapsible editCollapsible;
	
	@UiField
	HTMLPanel editStudentEmptyMessage;
	
	@UiField
	MaterialCollapsibleItem studentCollapseItem;
	
	@UiField
	MaterialCollapsibleBody studentCollapseBody;
	
	@UiField
	MaterialCollapsibleItem groupCollapseItem;
	
	@UiField
	MaterialCollapsibleItem furnitureCollapseItem;
	
	@UiField
	MaterialCollapsibleItem stationCollapseItem;
	
	private final RosterUtils utils;

	SelectionHandler<Widget> handler = new SelectionHandler<Widget>(){

		@Override
		public void onSelection(SelectionEvent<Widget> event) {
			String text = ((MaterialLink)event.getSelectedItem()).getText();
			stateSelectBtn.setText(text);
			stateSelectBtn.setIconType(((MaterialLink)event.getSelectedItem()).getIcon().getIconType());
			done();
			switch(text){
			case "Students": arrangeStudents(); editCollapsible.setActive(1); ;break;
			case "Groups": groups();editCollapsible.setActive(2);break;
			case "Stations": manageStations();editCollapsible.setActive(4);break;
			default:arrangeFurniture() ;editCollapsible.setActive(3);
			}
			
		}};
		
	ClickHandler clickHandler = new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			done();
		new Timer(){
			@Override 
			public void run(){
				String id = $(".active", editCollapsible).id();
				if(id == null || id.isEmpty()){
					return;
				}
				switch(id){
				case"studentCollapseItem": stateSelectBtn.setText("Students");
				stateSelectBtn.setIconType(IconType.SCHOOL);arrangeStudents();break;
				case"groupCollapseItem":stateSelectBtn.setText("Groups");
				stateSelectBtn.setIconType(IconType.GROUP_WORK);groups();break;
				case"furnitureCollapseItem":stateSelectBtn.setText("Furniture"); 
				stateSelectBtn.setIconType(IconType.EVENT_SEAT); arrangeFurniture();break;
				case"stationCollapseItem":stateSelectBtn.setText("Stations");
				stateSelectBtn.setIconType(IconType.WIDGETS);manageStations();break;
				}
			}
		}.schedule(300);
		
		}};	
	public SeatingChartPanel(RosterUtils ru) {
		utils = ru;	
		data = ru.getSeatingChart();
		oldData.setDescript(data.getDescript());
		oldData.setFurniture(data.getFurniture());
		oldData.setId(data.getId());
		oldData.setTitle(data.getTitle());
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onLoad(){
		final SeatingChartPanel p = this;
		console.log("seating chart onload called");
		drawGrid();	
		
		scDropDown.addSelectionHandler(handler);
		editCollapsible.addDomHandler(clickHandler, ClickEvent.getType());
		
	}//end onLoad
	
	@Override
	public void onUnload(){
		
	}
	
	public void setSeatingChart(SeatingChartJson seatingChart){
		data = seatingChart;
		drawGrid();
	}
	
	@Override
	public void drawGrid(){
		floorPlan.removeAllChildren();
		studentList.clear();
		console.log("Roster students is ");
		
		//make a copy of the student list then pop as they are put in place
		ArrayList<RosterStudentPanel> stuPanels = new ArrayList<RosterStudentPanel>();
		for(int i =0; i < utils.getStudents().length(); i++){
			//might as well setup the studentList here too
			RosterStudentPanel sp =new RosterStudentPanel(utils.getStudents().get(i));
			sp.chartStyle();
			stuPanels.add(sp);
		}
		console.log("We've cycled through students here is array of panels ");
		console.log(stuPanels);
		// go through list of furniture and place them on floorPlan
		console.log("Here is the furniture json ");
		console.log(data.getFurniture());
		if(data.getFurniture() != null && data.getFurniture().length() > 0){
		for(int i =0; i< data.getFurniture().length(); i++){
			console.log("draw(): furniture kind is ");
			console.log(data.getFurniture().get(i).getKind());
			//Gquery only operates on DOM so make and place 
			HTMLPanel furniturePanel = FurnitureUtils.byKind(data.getFurniture().get(i).getKind());
			floorPlan.appendChild(furniturePanel.getElement());
			
			final FurnitureJson furniture = data.getFurniture().get(i);
			
			$(furniturePanel).data("desk", furniture);
			$(furniturePanel).css("top",furniture.getTop());
			$(furniturePanel).css("left", furniture.getLeft());
			//now spin the desk
			$(furniturePanel.getElement()).find(".desk")
			.css("transform", "rotate(" + furniture.getRotate() +"rad)");
			
			///if this is a desk and it's not empty make a place student of student ids;
			if(furniture.getType() != null && !furniture.getType().isEmpty() &&furniture.getType().equals("desk")){
				console.log("If furniture desk called");
				JsArray<StudentSeatJson>studentSeats = furniture.getSeats();
				console.log("The seat of the desk are");
				console.log(studentSeats);
				for(int j = 0; j < studentSeats.length(); j++){
					$(furniturePanel).find(".pos" +(j+1)).data("seat", studentSeats.get(j));
					if(!studentSeats.get(j).isEmpty()){
						console.log("Student seat was not empty and \n studnet Id is " + studentSeats.get(j).getRosterStudent());
						//iterate through students for equal ids
						//then pop to make iterations shorter
						for(RosterStudentPanel rsp : stuPanels){
							rsp.chartStyle();
							console.log("student element id is " + rsp.getElement().getId());
							if(rsp.getElement().getId().equalsIgnoreCase(studentSeats.get(j).getRosterStudent())){
								console.log("studentId is equals rosterstudent seat called ");
									$(furniturePanel).find("tr>td>div.seat.pos"+(j+1))
										.append(rsp.toString()).find(".counterRotate").css("transform", "rotate("+ (-furniture.getRotate()) +"rad)");
									stuPanels.remove(rsp);
							}// end if
						}//end for iterate panel and hide ones that seats	
					}//end if
				}//end for j
			}//end if
		}// end for i///	
		
		}else{// furniture array is null so make a new one
			JsArray<FurnitureJson> furniture = JsArray.createArray().cast();
			data.setFurniture(furniture);
		}//end else
			//the rest of students panel go in the sideNav
			console.log("We finished with drawn seats and mathcing student id's lis of array is ");
			console.log(stuPanels);
			if(stuPanels.size() > 0){
				//place the rest in side
				for(RosterStudentPanel rsp : stuPanels){
				MaterialCollectionItem mci =new MaterialCollectionItem();
				rsp.chartStyle();
				mci.add(rsp);
				mci.setPadding(5);
				studentList.add(mci);
				}//end for
				console.log("Here is the student collection ");
				console.log(studentList);
			}//end if
	
		//everything should be in place now add home state
		home();
	}	
	
	public void done(){
		switch(state){
		case FURNITURE_EDIT: doneArrangeFurniture();break;
		case STUDENT_EDIT: doneArrangeStudents();break;
		case STATION_EDIT: doneManageStations();break;
		default: break;
		}
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
		$(".rosterStudent").click(new Function(){
			@Override 
			public void f(){
				//show student behavior form for
				// specific student
				//showBehaviorForm( studentId);
			}
		
		});
		
	}

	@Override
	public void home() {
		//right now home is for clicking on students
		//and generating a dialog for behavior management or
		// seeing more info on that student will calll it management dialog
		$(".rosterStudent").click(new Function(){
			@Override
			public void f(){
				// get the student id and populate the 
				//dialog
				$(body).trigger("manageStudentDialog", $(this).id());
			}
		});
		if(studentList.getElement().getChildCount() > 0){
			$(studentList).show();
			doneEditing();
		}
	}
	
	@Override
	public void unHome(){
		$(".rosterStudent").off("click");
		$(studentList).hide();
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
	


	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isEditing() {
		return isEditing;
	}
	
	@Override
	public void edit(){
		seatingChartToolbar.getElement().getStyle().setDisplay(Display.BLOCK);
		$(homeTools).css("display", "none");
		editTools.getStyle().setDisplay(Display.BLOCK);
		this.setIsEditing(true);
		if(studentList.getWidgetCount() >= 1){
			studentList.removeFromParent();
			studentCollapseBody.clear();
			studentCollapseBody.add(studentList);
		}
	}
	
	public void setIsEditing(boolean isEditing){
		this.isEditing = isEditing;
	}
	
	@Override
	public void doneEditing(){
		setIsEditing(false);
	}

	@Override
	public void cancel(String state) {
		// TODO Auto-generated method stub
		
	}

	public  void arrangeStudents(){
		//set state to student edit
		state = State.STUDENT_EDIT;
		//make students draggable
		//constraint to seatingchart
		Draggable.Options dragOpts = Draggable.Options.create();
		dragOpts.containment(".seatingChart").revert("invalid")
		.helper("clone").appendTo(body)
		//set the original to be lighter on start of drag
		.start(new Function(){
			@Override
			public void f(){
				$(this).css("opacity", "0.3");
				$dragParent = $(this).parent();
				console.log("drag parent is " + $dragParent.html());
			}
		})
		.stop(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				
				$(e.getEventTarget()).css("opacity", "1");
				return true;
			}
		})
		//while draggind
		.drag(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				DraggableUi ui = (DraggableUi)o[0];
				$(ui.getHelper().get()).find(".counterRotate").css("transform","rotate(0.0rad)");
				return true;
			}
		});
		$(".rosterStudent").as(Ui).draggable(dragOpts);
		//what happens once students are dropped
		Droppable.Options dropOpts = Droppable.Options.create();
		dropOpts.accept(".rosterStudent").greedy(true).hoverClass("seat-over");
		//make seat droppable event target is seaat
		$("td > div.seat", floorPlan).as(Ui).droppable(dropOpts)
			.on("drop", new Function(){
				@Override
				public boolean f(Event e, Object...o){
					DroppableUi ui =(DroppableUi)o[0];
					//once drop the draggable the target as parent how to get previous parent?
					GQuery $dropSeat = $(e.getEventTarget());
					GQuery $draggable = $(ui.draggable().get());
					//if the seatPanel has a student swap
					final GQuery $rosStudent = $dropSeat.find(".rosterStudent");
					if($rosStudent.length() > 0){
						if($dragParent.is("li")){
							$rosStudent.animate($$("{left: '+=75em' , opacity: 0}"),1000, EasingCurve.easeInSine, 
									new Function(){
								@Override
								public void f(){
									$rosStudent.appendTo($dragParent);
									$rosStudent.css($$("opacity:1;position:relative;left:0px"));
									
									
								}
							});
						}
						
							if($dragParent.hasClass("seat")){
							GQuery $pWrapper = $dropSeat.closest("div.desk-wrapper");
							final GQuery $cloneWrap = $("<div id='clone'></div>")
									.css($$("overflow:visible;width:4em;height:8em;position:absolute;left:"
							+ $pWrapper.left()  +"px;top:" + $pWrapper.top() +"px")).appendTo(floorPlan);
							floorPlan.appendChild($cloneWrap.get(0));
							console.log($cloneWrap);
							$rosStudent.appendTo($cloneWrap);
							$cloneWrap.animate($$("left:'+=" +($dragParent.offset().left -$pWrapper.offset().left)
									+"',top:'+=" +($dragParent.offset().top-$pWrapper.offset().top)+"'"), 350, EasingCurve.easeIn, 
							new Function(){
								@Override
								public void f(){
									$cloneWrap.remove();
									$rosStudent.appendTo($dragParent);
								}
							});
						}
					}
						
					
					
					
					//now detach and append to new seat
					$draggable.css("opacity","1");
				
					
					$draggable.appendTo($dropSeat.get(0));
					//remove former paren
					//actual seat
					StudentSeatJson stuSeat = $dropSeat.data("seat", StudentSeatJson.class);
					//parent desk
					FurnitureJson furJ =  $dropSeat.closest("div.desk-wrapper").data("desk", FurnitureJson.class);
					if(stuSeat == null){
					
						//find pos class of target  until positions match
						//more practically 2 will be the highest number revie when we revise desks.
							if($(e.getEventTarget()).hasClass("pos1")){
								stuSeat = furJ.getSeatByNum(1);
							}else{
								stuSeat = furJ.getSeatByNum(2);
							}//end if-else
							
							$(e.getEventTarget()).data("seat", stuSeat);
						
					}
					stuSeat.setRosterStudent(ui.draggable().get().getId());
					$(e.getEventTarget()).find("div.counterRotate").css("transform", "rotate("+(-furJ.getRotate())+"rad)");
					console.log("seat json");
					console.log($(e.getEventTarget()).data("seat", StudentSeatJson.class));
					return true;
				}
			});
		
		
	};
	
	public void doneArrangeStudents(){
		MaterialLoader.showLoading(true);
		SeatingChartJson seatingChart = window.getPropertyJSO("seatingChart").cast();
		//iterate through the seats and update data
		$(".seat").each(new Function(){
			@Override
			public void f(){
				StudentSeatJson seat = $(this).data("seat",StudentSeatJson.class);
				if($(this).find(".rosterStudent").length() > 0){
					
					seat.setRosterStudent($(this).find(".rosterStudent").get(0).getId());
				}else{
					seat.setRosterStudent("");
				}
			}
		});
		console.log("This is the data sent by done arrage furniture: " +  JsonUtils.stringify(seatingChart));
		$(".rosterStudent").as(Ui).draggable().destroy();
		$("td > div.seat",floorPlan).as(Ui).droppable().destroy();
		Properties prop = Properties.create();
		prop.set("seatingChart", JsonUtils.stringify(data));
		prop.set("roster", utils.getCurrentRoster().getId());
		Ajax.post(RosterUrl.seatingchart(utils.getCurrentRoster().getId(), utils.getSelectedClassTime().getRoutine().getId()), prop);
		MaterialLoader.showLoading(false);
	};
	
	
	
	public  void arrangeFurniture(){
		state = State.FURNITURE_EDIT;
		tempFurnitureList = new ArrayList<FurnitureJson>();
		if(data.getFurniture().length() > 0){
		for(int i = 0; i < data.getFurniture().length(); i++){
			tempFurnitureList.add(data.getFurniture().get(i));
		}
		}//end if
		
	//make icons draggable and floorPlan drop target
		Draggable.Options dragOpts = Draggable.Options.create();
	    
		dragOpts.zIndex(500)
		.appendTo(body)
		.cursorAt(CursorAt.create())
		.revert("invalid")
		.containment(".seatingChart")
		.helper("clone");
		
		$("img.furnitureIcon").as(Ui).draggable(dragOpts);
		
		Droppable.Options dropOpts = Droppable.Options.create();
		dropOpts.accept(".furnitureIcon").greedy(true);
		$(floorPlan).as(Ui).droppable(dropOpts).on(Droppable.Event.drop, new Function(){
			@Override
			public boolean f(Event e,Object...ui){
				e.stopPropagation();
				e.preventDefault();
			DroppableUi dropUi = (DroppableUi) ui[0];
			//get the kind from the droppable helper
			String iconId = dropUi.draggable().get().getId();
			console.log("droppable iconId is : " + iconId);
			//html rep of desk
			final HTMLPanel drop = FurnitureUtils.byIconId(iconId);
			//object rep of desk
			//we should have a furniture object here////////
			final FurnitureJson desk = FurnitureJson.create();
			desk.setSeats(iconId);
			console.log(desk);
			
			String left = e.getClientX()  - floorPlan.getAbsoluteLeft()+ body.getScrollLeft()+"px";
			String top = e.getClientY() - floorPlan.getAbsoluteTop()+ body.getScrollTop() + "px";
			//added to browser
			floorPlan.appendChild(drop.getElement());
			GQuery $drop = $(drop);
			$drop.css("left",left);
			$drop.css("top",top );
			
			
			//push to temp list
			desk.setLeft($drop.css("left"));
			desk.setTop($drop.css("top"));
			tempFurnitureList.add(desk);
			
			//push data to desk
			$drop.data("desk", desk);
			
			console.log("The furniture list array");
			console.log(tempFurnitureList);
			makeDragRotateDelete($drop);
				return true;
			}
		});
		
		//make furniture on floorPlan draggable and rotatable and deletable
		$(".desk-wrapper", floorPlan).each(new Function(){
			@Override
			public void f(){
				final GQuery $this = $(this);
				makeDragRotateDelete($this);
				
			}
		});
	};
	
	private void makeDragRotateDelete(final GQuery $this){
		final FurnitureJson thisDesk = $this.data("desk");
		Draggable.Options dragOpt3 =Draggable.Options.create();
		CursorAt cursorAt = CursorAt.create();
		cursorAt.setTop((int) Math.floor($this.height()/2)).setLeft((int)Math.floor($this.width()/2));
		dragOpt3.containment(floorPlan).cursorAt(cursorAt)
		.stop(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				thisDesk.setTop($this.css("top"));
				thisDesk.setLeft($this.css("left"));
				//get true left
				console.log(thisDesk);
				return true;
			}
		});
		
		Rotatable.Options rotOpt2 = Rotatable.Options.create();
		console.log("rotate of desk is " + thisDesk.getRotate());
		
		rotOpt2.angle(thisDesk.getRotate()).stop(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				RotatableUi ui = (RotatableUi)o[0];
				GQuery $desk = $this.find(".desk");
				thisDesk.setRotate(ui.angle().current());
				thisDesk.setTransform($desk.css("transform"));
				console.log(thisDesk);
				return true;
			}
		})
		.rotate(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				console.log("rotate called");
				RotatableUi ui = (RotatableUi)o[0];
				console.log("rotatable ui is ");
				console.log(ui);
				GQuery $counter = $(ui.element()).find("div.counterRotate");
				console.log(ui.element());
				console.log("all counter roatate found " + $counter.size());
				if($counter.length() > 0){
					$counter.css("transform","rotate(" + -ui.angle().current() +"rad)");
				}
				return true;
			}
		});
		$this.as(Ui).draggable(dragOpt3);
		$this.find(".desk").as(Ui).rotatable(rotOpt2);
		$(".deskDeleter").css("display", "block").on("click", new Function(){
										@Override 
										public boolean f(Event e){ 
											
											GQuery $desk = $(this).closest(".desk-wrapper");
											deleteFurniture($desk);
											return true;}});
	}
	
	public void doneArrangeFurniture(){
		JsArray<FurnitureJson> finalList = JsArray.createArray().cast();
		$(".desk-wrapper",floorPlan).as(Ui).draggable().destroy();
		$(".furnitureIcon").as(Ui).draggable().destroy();
		$(".desk", floorPlan).as(Ui).rotatable().destroy();
		$(floorPlan).as(Ui).droppable().destroy();
		$(".deskDeleter").off("click").css("display", "none");
		
		//set the temp to permanent and redraw
		//iterate throug templist exclude nulls 
		for(int i = 0; i < tempFurnitureList.size() ; i++){
			if(tempFurnitureList.get(i) == null){
				continue;
			}else{
				finalList.push(tempFurnitureList.get(i));
			}
		}
		data.setFurniture(finalList);
		
		finalList = null;
		tempFurnitureList = null;
			
		console.log("This is the data sent by done arrage furniture: " +  JsonUtils.stringify(data));
		Properties prop = Properties.create();
		prop.set("seatingChart", JsonUtils.stringify(data));
		prop.set("roster", utils.getCurrentRoster().getId());
		
		Ajax.post(RosterUrl.seatingchart(utils.getCurrentRoster().getId(), utils.getSelectedClassTime().getRoutine().getId()), prop);
		drawGrid();
	};
	
	

	public void deleteFurniture(final GQuery $this){
		//first check to see if it's a desk and if the desk has students
		//if so these student must be put in the studentList alphabetically??
		GQuery $desk = $this;
		
			GQuery $rosStu = $desk.find(".rosterStudent");
			if($rosStu.length() > 0){
				for(int i = 0; i < $rosStu.length(); i++){
				MaterialCollectionItem link = new MaterialCollectionItem();
				link.add($rosStu.widget());
				}//end for
			}//end if
	
		tempFurnitureList.remove($desk.data("desk"));
		$desk.remove();
	}

	public void manageStations() {
		// TODO Auto-generated method stub
		
	}

	public void doneManageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getState() {
		return this.state;
	}

	@Override
	public void setState(State state) {
		this.state = state;
	}
	
}
