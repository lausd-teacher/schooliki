package net.videmantay.roster.views.routine;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;
import static gwtquery.plugins.ui.Ui.Ui;

import java.util.ArrayList;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwtquery.plugins.ui.DraggableUi;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.RotatableUi;
import gwtquery.plugins.ui.interactions.CursorAt;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;
import gwtquery.plugins.ui.interactions.Rotatable;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.routine.json.FullRoutineJson;
import net.videmantay.roster.routine.json.FurnitureJson;
import net.videmantay.roster.routine.json.RoutineJson;
import net.videmantay.roster.routine.json.SeatingChartJson;
import net.videmantay.roster.routine.json.StudentSeatJson;
import net.videmantay.roster.views.RosterStudentPanel;
import net.videmantay.shared.Action;
import gwt.material.design.addins.client.richeditor.MaterialRichEditor;
import gwt.material.design.addins.client.richeditor.base.constants.ToolbarButton;

public class RoutineForm extends Composite {

	private static RoutineFormUiBinder uiBinder = GWT.create(RoutineFormUiBinder.class);

	interface RoutineFormUiBinder extends UiBinder<Widget, RoutineForm> {
	}
	
	
	@UiField
	public MaterialButton submitBtn;
	
	@UiField
	public MaterialButton cancelBtn;
		
	@UiField
	public MaterialTextBox title;
	
	@UiField
	public MaterialTextArea description;
	
	@UiField
	public MaterialCheckBox  isDefault;
	
	@UiField
	public MaterialPanel scEditFloorPlan;
	
	@UiField
	MaterialCollection studentList;
	
	private final FullRoutineJson originalData;
	private FullRoutineJson copyData;
	private SeatingChartJson data;
	private ArrayList<FurnitureJson> tempFurnitureList;
	//action list for undos
		private final Stack<Action> stack = new Stack<Action>();
	private final RosterUtils utils;
	
	public RoutineForm(RosterUtils ru, FullRoutineJson frj, SeatingChartJson scj) {
		utils = ru;
		originalData = frj;
		copyData = FullRoutineJson.createObject().cast();
		copyData.copy(frj);
		data = scj;
		console.log("Routine Form constructor");
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	

	public RoutineJson getFormData(){
		RoutineJson formData = JavaScriptObject.createObject().cast();
		
		formData.setDescript(description.getText());
		formData.setTitle(title.getText());
		formData.setIsDefault(isDefault.getValue());
		
		return formData;
	}
	
	
	public MaterialTextBox getClassTimeTitle() {
		return this.title;
	}


	public MaterialTextArea getDescription() {
		return this.description;
	}


	public  void arrangeStudents(){
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
		$("td > div.seat", scEditFloorPlan).as(Ui).droppable(dropOpts)
			.on("drop", new Function(){
				@Override
				public boolean f(Event e, Object...o){
					DroppableUi ui =(DroppableUi)o[0];
				//once dropped student
					console.log(ui.draggable().get());
					//clean up data of seat incase of parent seat
					if($(ui.draggable().get()).parent().hasClass("seat")){
					StudentSeatJson sSeat = 	$(ui.draggable().get()).parent().data("seat", StudentSeatJson.class);
						if(sSeat != null){
						sSeat.setRosterStudent("");
						}//inner if
					}//outer if
					
					
					
					final GQuery $seatPanel = $(e.getEventTarget());
					
					//if the seatPanel has a student swap
					GQuery $studentCheck = $seatPanel.find(".rosterStudent");
					if($studentCheck.length() > 0){
						$studentCheck.detach();
						GQuery $parent = $(ui.draggable().get()).parent();
						$parent.append($studentCheck);
						if($parent.hasClass("seat")){
							StudentSeatJson sSeat = 	$parent.data("seat", StudentSeatJson.class);
							if(sSeat == null){
								sSeat = StudentSeatJson.createObject().cast();
									if($parent.hasClass("pos2")){
										sSeat.setSeatNum(2);
									}else{
									sSeat.setSeatNum(1);
									}
									$parent.data("seat",sSeat);
								}// if seat null
							sSeat.setRosterStudent($studentCheck.id());
							double rotateDegree = $parent.closest(".desk-wrapper").data("desk",FurnitureJson.class).getRotate();
							$studentCheck.find("div.counterRotate").css("transform", "rotate("+(-rotateDegree)+"rad)");
							}// if parent has class seat
						else{
							$studentCheck.find("div.counterRotate").css("transform", "rotate("+0+"rad)");
						}
						}//outer if
					
					
					//now detach and append to new seat
					$(ui.draggable().get()).css("opacity","1");
					$(ui.draggable().get()).detach();
					$seatPanel.append(ui.draggable().get());
					//actual seat
					StudentSeatJson stuSeat = $seatPanel.data("seat", StudentSeatJson.class);
					//parent desk
					FurnitureJson furJ =  $seatPanel.closest(".desk-wrapper").data("desk", FurnitureJson.class);
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
		MaterialLoader.showLoading(true, scEditFloorPlan);
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
		$("td > div.seat",scEditFloorPlan).as(Ui).droppable().destroy();
		Properties prop = Properties.create();
		prop.set("seatingChart", JsonUtils.stringify(data));
		prop.set("roster", utils.getCurrentRoster().getId());
		Ajax.post(RosterUrl.seatingchart(utils.getCurrentRoster().getId(), utils.getSelectedClassTime().getRoutine().getId()), prop);
		drawGrid();
		MaterialLoader.showLoading(false,scEditFloorPlan);
	};
	
	
	
	public  void arrangeFurniture(){
		tempFurnitureList = new ArrayList<FurnitureJson>();
		if(data.getFurniture().length() > 0){
		for(int i = 0; i < data.getFurniture().length(); i++){
			tempFurnitureList.add(data.getFurniture().get(i));
		}
		}//end if
		
	//make icons draggable and scEditFloorPlan drop target
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
		$(scEditFloorPlan.getElement()).as(Ui).droppable(dropOpts).on(Droppable.Event.drop, new Function(){
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
			
			String left = e.getClientX()  - scEditFloorPlan.getAbsoluteLeft()+ body.getScrollLeft()+"px";
			String top = e.getClientY() - scEditFloorPlan.getAbsoluteTop()+ body.getScrollTop() + "px";
			//added to browser
			scEditFloorPlan.add(drop);
			$(drop).css("left",left);
			$(drop).css("top",top );
			//push to temp list
			desk.setLeft($(drop).css("left"));
			desk.setTop($(drop).css("top"));
			tempFurnitureList.add(desk);
			
			//push data to desk
			$(drop).data("desk", desk);
			
			console.log("The furniture list array");
			console.log(tempFurnitureList);
			makeDragRotateDelete($(drop));
			
			//stack.push(new FurnitureAddAction(drop,(JsArray<FurnitureJson>) tempFurnitureList));
			console.log(stack);
				return true;
			}
		});
		
		//make furniture on scEditFloorPlan draggable and rotatable and deletable
		$(".desk-wrapper", scEditFloorPlan).each(new Function(){
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
		dragOpt3.containment(scEditFloorPlan.getElement()).cursorAt(cursorAt)
		.stop(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				thisDesk.setTop($this.css("top"));
				thisDesk.setLeft($this.css("left"));
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
				thisDesk.setRotate(ui.angle().current());
				thisDesk.setTransform($this.find(".desk").css("transform"));
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
		$(".desk-wrapper",scEditFloorPlan).as(Ui).draggable().destroy();
		$(".furnitureIcon").as(Ui).draggable().destroy();
		$(".desk", scEditFloorPlan).as(Ui).rotatable().destroy();
		$(scEditFloorPlan).as(Ui).droppable().destroy();
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
		
		$(studentList).hide();
		
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
				MaterialLink link = new MaterialLink();
				$(link.getElement()).append($rosStu.get(i));
				studentList.add(link);
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
	
	public void drawGrid(){
	
		console.log("Roster students is ");
		
		//make a copy of the student list then pop as they are put in place
		ArrayList<RosterStudentPanel> stuPanels = new ArrayList<RosterStudentPanel>();
		for(int i =0; i < utils.getStudents().length(); i++){
			//might as well setup the studentList here too
			RosterStudentPanel sp =new RosterStudentPanel(utils.getStudents().get(i));
			
			MaterialLink link = new MaterialLink();
			link.add(sp);
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
			scEditFloorPlan.add(furniturePanel);
			
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
			console.log("We finished with drawn seats and matching student id's lis of array is ");
			console.log(stuPanels);
			if(stuPanels.size() > 0){
				//place the rest in side
				for(RosterStudentPanel rsp : stuPanels){
				MaterialCollectionItem mci =new MaterialCollectionItem();
				mci.add(rsp);
				studentList.add(mci);
				}//end for
				console.log("Here is the student collection ");
				console.log(studentList);
			}//end if
	}

}
