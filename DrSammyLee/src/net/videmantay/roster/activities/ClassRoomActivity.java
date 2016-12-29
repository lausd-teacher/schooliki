package net.videmantay.roster.activities;

import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SingleSelectionModel;

import gwt.material.design.addins.client.masonry.MaterialMasonry;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.AppUserJson;
import net.videmantay.roster.json.GradedWorkJson;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.places.AssignementPlace;
import net.videmantay.roster.places.BookPlace;
import net.videmantay.roster.places.ClassRoomPlace;
import net.videmantay.roster.places.ClassTimePlace;
import net.videmantay.roster.places.FormPlace;
import net.videmantay.roster.places.GoalPlace;
import net.videmantay.roster.places.IncidentPlace;
import net.videmantay.roster.places.JobPlace;
import net.videmantay.roster.places.LessonPlanPlace;
import net.videmantay.roster.places.RosterHomePlace;
import net.videmantay.roster.views.AppLayout;
import net.videmantay.roster.views.ClassRoomGrid;
import net.videmantay.roster.views.RosterDashboardPanel;
import net.videmantay.roster.views.RosterStudentCard;
import net.videmantay.roster.views.RosterStudentPanel;
import net.videmantay.roster.views.StudentActionModal;
import net.videmantay.roster.views.assignment.AssignementDashboard;
import net.videmantay.roster.views.assignment.AssignementTable;
import net.videmantay.roster.views.assignment.AssignmentGrid;
import net.videmantay.roster.views.assignment.EmptyAssignmentGrid;
import net.videmantay.roster.views.assignment.GradedWorkForm;
import net.videmantay.roster.views.assignment.GradedWorkMain;
import net.videmantay.roster.views.classtime.ClassTimeForm;
import net.videmantay.roster.views.classtime.ClassTimeGrid;
import net.videmantay.roster.views.classtime.ClasstimeGridItem;
import net.videmantay.roster.views.classtime.SeatingChartPanel;
import net.videmantay.roster.views.components.ClassRoomSideNav;
import net.videmantay.roster.views.components.IncidentFormIconInput;
import net.videmantay.roster.views.components.StudentIncidentCard;
import net.videmantay.roster.views.draganddrop.SelectionManager;
import net.videmantay.roster.views.incident.IncidentForm;
import net.videmantay.roster.views.incident.IncidentMain;
import net.videmantay.roster.views.lessonPlan.LessonPlanMain;
import net.videmantay.roster.views.student.CreateStudentForm;
import net.videmantay.roster.views.student.StudentCard;
import net.videmantay.student.json.RosterStudentJson;



public class ClassRoomActivity extends AbstractActivity implements ClassRoomSideNav.Presenter,
    RosterDashboardPanel.Presenter, ClassRoomGrid.Presenter, CreateStudentForm.Presenter, GradedWorkMain.Presenter,
		GradedWorkForm.Presenter, IncidentMain.Presenter, IncidentForm.Presenter, 
		ClassTimeGrid.Presenter, ClassTimeForm.Presenter,
		SeatingChartPanel.Presenter, StudentActionModal.Presenter
		,IncidentFormIconInput.Presenter  {

	final ClientFactory factory;

	RosterDashboardPanel dashboard;
	Place currentPlace;
	AppLayout appPanel;
	ClassRoomSideNav classRoomSideNav;
	GradedWorkMain gradedWorkMain;
	ClassRoomGrid grid;
	SeatingChartPanel seatingChart;
	JsArray<GradedWorkJson> gradedWorkList = JavaScriptObject.createObject().cast();
	JsArray<IncidentJson> incidentsList = JavaScriptObject.createObject().cast();
	JsArray<ClassTimeJson> currentRosterClassTimesList = JavaScriptObject.createObject().cast();
	JsArray<IncidentTypeJson> incidentTypesList = JavaScriptObject.createObject().cast();
	AsyncDataProvider<GradedWorkJson> providesKey;
	AssignmentGrid assignementGrid;
	IncidentMain incidentMainPage;
	IncidentForm incidentForm;
	ClassTimeGrid classTimegrid;
	AssignementDashboard assignementDashboard;
	boolean isGridEmpty = true;
	boolean isIconsInputDisplayed = false;
	IncidentFormIconInput iconInput = null;
	StudentActionModal studentActionModal;
	boolean firstCalendarLoad = true;


	public ClassRoomActivity(ClientFactory factory, Place place) {
		this.factory = factory;
		this.dashboard = factory.getRosterDashBoard();
		this.appPanel = factory.getAppPanel();
		this.currentPlace = place;
		this.classRoomSideNav = factory.getClassRoomSideNav();
		this.grid = factory.getClassRoomGrid();
		this.gradedWorkMain = factory.getGradedWorkMain();
		this.seatingChart = factory.getSettingChartPanel();
		this.assignementGrid = consctructAssignementGridTable();
		gradedWorkMain.setAssignementGrid(new AssignementTable());
		this.incidentMainPage = factory.getIncidentMainPage();
		this.incidentForm = incidentMainPage.getIncidentForm();
		this.classTimegrid = factory.getClassTimeGrid();
		SelectionManager.registerDocumentClickEvent();
		iconInput = factory.getIncidentFormInput();
		incidentTypesList = factory.getIncidentTypesList();
		studentActionModal = factory.getStudentActionModal();
		initializeEvents();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		appPanel.getSideNav().clear();

		appPanel.getSideNav().add(factory.userProfile());
		appPanel.getSideNav().add(classRoomSideNav.getDashboardLink());
		appPanel.getSideNav().add(classRoomSideNav.getAssignmentLink());
		appPanel.getSideNav().add(classRoomSideNav.getLessonPlanLink());
		appPanel.getSideNav().add(classRoomSideNav.getIncidentLink());
		appPanel.getSideNav().add(classRoomSideNav.getGoalLink());
		appPanel.getSideNav().add(classRoomSideNav.getJobLink());
		appPanel.getSideNav().add(classRoomSideNav.getBookLink());
		appPanel.getSideNav().add(classRoomSideNav.getClassTimeLink());
		appPanel.getSideNav().add(classRoomSideNav.getFormLink());
		appPanel.getSideNav().add(classRoomSideNav.getLogoutLink());

		// Adding Nav Bar
		appPanel.getNavBartitle().setText(factory.getCurrentRoster().getTitle());
		appPanel.getNavBarContainer().clear();
		appPanel.getMainPanel().clear();
		hideSideNav();
		if (currentPlace instanceof ClassRoomPlace) {
			dashboard.clearDropDown();
			getClassTimesForCurrentRosterAndDrawClassTimeGrid();
			getRosterStudentsListAndDrawGrid();
			dashboard.setDisplayInTab1(grid);
			appPanel.getMainPanel().add(dashboard);
		} else if (currentPlace instanceof AssignementPlace) {
			getGradedWorkListAndDrawGrid();
			assignementDashboard = factory.getAssignementDashboard();
			appPanel.getMainPanel().add(assignementDashboard);
		} else if (currentPlace instanceof LessonPlanPlace) {
			appPanel.getMainPanel().add(new Label("LessonPlan view is not implemented yet"));
		} else if (currentPlace instanceof IncidentPlace) {
			//getIncidentsAndDraw();
			appPanel.getMainPanel().add(incidentMainPage);
		} else if (currentPlace instanceof GoalPlace) {
			appPanel.getMainPanel().add(new Label("Goal view is under construction"));
		} else if (currentPlace instanceof JobPlace) {
			appPanel.getMainPanel().add(new Label("Job view is under construction"));
		} else if (currentPlace instanceof BookPlace) {
			appPanel.getMainPanel().add(new Label("BookPlace view is not implemented yet"));
		} else if (currentPlace instanceof ClassTimePlace) {
			drawClassTimeGrid();
			appPanel.getMainPanel().add(classTimegrid);
		} else if (currentPlace instanceof FormPlace) {
			appPanel.getMainPanel().add(new Label("FormPlace view is under construction"));
		}

		panel.setWidget(appPanel);

		providesKey = new AsyncDataProvider<GradedWorkJson>() {
			@Override
			protected void onRangeChanged(final HasData<GradedWorkJson> display) {
				GWT.log("range is changing");
			}

			@Override
			public Long getKey(GradedWorkJson value) {
				return value.getId();
			}
		};

	}

	private void hideSideNav() {
		new Timer() {
			@Override
			public void run() {
				appPanel.getSideNav().hide();
			}
		}.schedule(250);
	}

	private void goTo(Place place) {
		  if(SelectionManager.isSelectionActive()){
			  SelectionManager.unSelect(SelectionManager.getSelection());
		  }
		  if(factory.isEditMode()){
				if(Window.confirm("are you sure you want to quit the seating chart with exiting the edit mode, "
						+ "you will lose all your changes next time? ")){
					
					
					dashboard.showToolBar();
					factory.setEditMode(false);
					disableEditingInSeatingChart();
					
				}
			}
		factory.getPlaceController().goTo(place);
	}

	@Override
	public void dashboardLinkClickEvent() {
		classRoomSideNav.getDashboardLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new ClassRoomPlace("classroom"));
			}

		});

	}

	@Override
	public void assignmentLinkClickEvent() {
		classRoomSideNav.getAssignmentLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new AssignementPlace("assignement"));

			}

		});

	}

	@Override
	public void lessonPlanLinkClickEvent() {
		classRoomSideNav.getLessonPlanLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new LessonPlanPlace("lessonPlan"));

			}
		});
	}

	@Override
	public void incidentLinkClickEvent() {
		classRoomSideNav.getIncidentLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new IncidentPlace("incident"));

			}
		});

	}

	@Override
	public void goalLinkClickEvent() {
		classRoomSideNav.getGoalLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new GoalPlace("goal"));
			}
		});
	}

	@Override
	public void jobLinkClickEvent() {
		classRoomSideNav.getJobLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new JobPlace("job"));
			}

		});

	}

	@Override
	public void bookLinkClickEvent() {
		classRoomSideNav.getBookLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new BookPlace("book"));

			}

		});

	}

	@Override
	public void classTimeClickEvent() {
		classRoomSideNav.getClassTimeLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new ClassTimePlace("classtime"));
			}

		});

	}

	@Override
	public void formClickEvent() {
		classRoomSideNav.getFormLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new FormPlace("form"));

			}

		});

	}

	@Override
	public void logoutLinkClickEvent() {
		classRoomSideNav.getLogoutLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new RosterHomePlace("rosters"));

			}

		});

	}

	private AssignmentGrid consctructAssignementGridTable() {

		// Cannot be injected because it is redrawn on each roster
		AssignmentGrid assignementGrid = new AssignmentGrid(providesKey);

		TextColumn<GradedWorkJson> titleCol = new TextColumn<GradedWorkJson>() {

			@Override
			public String getValue(GradedWorkJson object) {
				return object.getTitle();
			}
		};

		TextColumn<GradedWorkJson> typeCol = new TextColumn<GradedWorkJson>() {

			@Override
			public String getValue(GradedWorkJson object) {
				return object.getGradedWorkType();
			}
		};

		TextColumn<GradedWorkJson> subjectCol = new TextColumn<GradedWorkJson>() {
			@Override
			public String getValue(GradedWorkJson object) {
				return object.getSubject();
			}
		};

		TextColumn<GradedWorkJson> dueDateCol = new TextColumn<GradedWorkJson>() {

			@Override
			public String getValue(GradedWorkJson object) {
				return object.getDueDate();
			}
		};

		TextColumn<GradedWorkJson> assignedDateCol = new TextColumn<GradedWorkJson>() {

			@Override
			public String getValue(GradedWorkJson object) {
				return object.getSubject();
			}
		};

		NumberCell pointsCell = new NumberCell();
		Column<GradedWorkJson, Number> pointsCol = new Column<GradedWorkJson, Number>(pointsCell) {

			@Override
			public Number getValue(GradedWorkJson object) {
				return object.getPointsPossible();
			}
		};

		SafeHtmlCell isGradedCell = new SafeHtmlCell();
		Column<GradedWorkJson, SafeHtml> isGradedCol = new Column<GradedWorkJson, SafeHtml>(isGradedCell) {
			@Override
			public SafeHtml getValue(GradedWorkJson object) {
				SafeHtml html = SafeHtmlUtils.fromString("<i class='material-icons'>done</i>");

				return html;
			}
		};
		AbstractCell<GradedWorkJson> actionCell = new AbstractCell<GradedWorkJson>("click") {

			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context, GradedWorkJson value,
					SafeHtmlBuilder sb) {
				final String html = "<div><i class='material-icons gotoGradedWork' style='color:Green'>arrow_right</i></div>";
				sb.appendHtmlConstant(html);

			}

			@Override
			public void onBrowserEvent(Context context, Element parent, GradedWorkJson value, NativeEvent event,
					ValueUpdater<GradedWorkJson> valueUpdater) {
				if (event.getType().equalsIgnoreCase("click")) {
					// $(body).trigger("gotoGradedWork",value);
				}
			}

			@Override
			public void onEnterKeyDown(Context context, Element parent, GradedWorkJson value, NativeEvent event,
					ValueUpdater<GradedWorkJson> valueUpdater) {
				// $(body).trigger("gotoGradedWork",value);
			}

		};

		IdentityColumn<GradedWorkJson> actionCol = new IdentityColumn<>(actionCell);

		assignementGrid.addStyleName("striped responsive-table");
		assignementGrid.addColumn(titleCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Title</span>"));
		assignementGrid.addColumn(dueDateCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Due<br/> Date</span>"));
		assignementGrid.addColumn(typeCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Type</span>"));
		assignementGrid.addColumn(subjectCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Subject</span>"));
		assignementGrid.addColumn(pointsCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Points</span>"));
		assignementGrid.addColumn(assignedDateCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Assigned<br/> Date</span>"));
		assignementGrid.addColumn(isGradedCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Scored</span>"));
		assignementGrid.addColumn(actionCol);
		assignementGrid.setSelectionModel(new SingleSelectionModel<GradedWorkJson>());
		assignementGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		assignementGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		assignementGrid.setFocus(true);
		assignementGrid.setEmptyTableWidget(new EmptyAssignmentGrid());

		return assignementGrid;

	}

	public void setPlace(Place place) {

		appPanel.getMainPanel().clear();
		appPanel.getSideNav().clear();

		appPanel.getSideNav().add(factory.userProfile());
		appPanel.getSideNav().add(classRoomSideNav.getDashboardLink());
		appPanel.getSideNav().add(classRoomSideNav.getAssignmentLink());
		appPanel.getSideNav().add(classRoomSideNav.getLessonPlanLink());
		appPanel.getSideNav().add(classRoomSideNav.getIncidentLink());
		appPanel.getSideNav().add(classRoomSideNav.getGoalLink());
		appPanel.getSideNav().add(classRoomSideNav.getJobLink());
		appPanel.getSideNav().add(classRoomSideNav.getBookLink());
		appPanel.getSideNav().add(classRoomSideNav.getClassTimeLink());
		appPanel.getSideNav().add(classRoomSideNav.getFormLink());
		appPanel.getSideNav().add(classRoomSideNav.getLogoutLink());

		// Adding Nav Bar
		appPanel.getNavBartitle().setText(factory.getCurrentRoster().getTitle());
		appPanel.getNavBarContainer().clear();
		hideSideNav();

		currentPlace = place;
		

		if (place instanceof ClassRoomPlace) {
			//No need to draw Again 
			//getRosterStudentsListAndDrawGrid();
			dashboard.getGridSwitch().setValue(false);
			appPanel.getMainPanel().add(dashboard);
		} else if (place instanceof AssignementPlace) {
			getGradedWorkListAndDrawGrid();
			//lazy loading
			assignementDashboard = factory.getAssignementDashboard();
			appPanel.getMainPanel().add(assignementDashboard);
		} else if (place instanceof LessonPlanPlace) {
			appPanel.getMainPanel().add(new  LessonPlanMain());
		} else if (place instanceof IncidentPlace) {
			//getIncidentsAndDraw();
			appPanel.getMainPanel().add(incidentMainPage);
		} else if (place instanceof GoalPlace) {
			appPanel.getMainPanel().add(new Label("Goal view is not implemented yet"));
		} else if (place instanceof JobPlace) {
			appPanel.getMainPanel().add(new Label("Job view is not implemented yet"));
		} else if (place instanceof BookPlace) {
			appPanel.getMainPanel().add(new Label("BookPlace view is not implemented yet"));
		} else if (place instanceof ClassTimePlace) {
			drawClassTimeGrid();
			appPanel.getMainPanel().add(classTimegrid);
		} else if (place instanceof FormPlace) {
			appPanel.getMainPanel().add(new Label("FormPlace view is not implemented yet"));
		}

	}

	private void initializeEvents() {
		WindowResizeHandler();
		dashboardLinkClickEvent();
		assignmentLinkClickEvent();
		lessonPlanLinkClickEvent();
		incidentLinkClickEvent();
		goalLinkClickEvent();
		bookLinkClickEvent();
		classTimeClickEvent();
		formClickEvent();
		logoutLinkClickEvent();
		gridSwitchClickEvent();
		homeworkIconClickEvent();
		addStudentButtonClickEvent();
		okButtonClickHandler();
		cancelButtonClickHandler();
		tabsClickEvent();
		assignementGridFabClick();
		gradedWorkFromOkButtonClickEvent();
		gradedWorkFromCancelButtonClickEvent();
		addIncidentFABButtonClickEvent();
		doneButtonClickEvent();
		cancelButtonClickEvent();
		manageClassTimeLinkClickEvent();
		classTimeAddButtonClickEvent();
        createClassTimeFormSubmitButton();
        createClassTimeFormCancelButton();
    	seatingChartEditClickEvent();
        barDoneButtonClickEvent();
	    barCancelButtonClickEvent();
	    studentActionModalOkButtonClickEvent();
	    incidentFormSelectedIconClickEvent();
	    removeSeatingChartActionButtonClickEvent();
	    rollIconClick();
	    saveRollButtonClick();
	    cancelRollButtonClick();
	    editingDivClickEvent();
	    undoButtonClickEvent();
	    redoButtonClickEvent();
	}

	@Override
	public void gridSwitchClickEvent() {

		dashboard.getGridSwitch().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (dashboard.getViewType() == RosterDashboardPanel.View.GRID) {
					dashboard.setViewType(RosterDashboardPanel.View.CHART);
					dashboard.setDisplayInTab1(seatingChart);
				} else {
					if(factory.isEditMode()){
						if(Window.confirm("are you sure you want to quit the seating chart with exiting the edit mode, "
								+ "you will lose all your changes next time? ")){
							dashboard.showToolBar();
							factory.setEditMode(false);
							disableEditingInSeatingChart();
						}
					}
					dashboard.setViewType(RosterDashboardPanel.View.GRID);
					dashboard.setDisplayInTab1(grid);
				}
			}
		});

	}

	private void drawGrid(boolean sortbyFirstName) {

		// dashboard.getTab1Main().clear();
		MaterialRow row = new MaterialRow();
		MaterialRow row2 = new MaterialRow();
		grid.getContainer().clear();
		seatingChart.getStudentsPanel().clear();
		
		grid.getContainer().add(row);
		seatingChart.getStudentsPanel().add(row2);
		
		int i = 0;
		//add students to both seating chart and grid
		while (i < factory.getCurrentRosterStudentList().length()) {
			MaterialColumn c = new MaterialColumn();
			MaterialColumn c2 = new MaterialColumn();
			
			RosterStudentCard rsp = new RosterStudentCard(factory.getCurrentRosterStudentList().get(i), this.factory);
			RosterStudentPanel seatingChartStudent = new RosterStudentPanel(factory.getCurrentRosterStudentList().get(i));
			rsp.addStyleName("grid");
			c.add(rsp);
			i++;
			row.add(c);
			
			//For seating chart grid
			c2.add(seatingChartStudent);
			
			   if( i != 0 && i % 3 == 0){
				   row2 = new MaterialRow();
				   //there is row above so make some space betwwen the two
				   row2.setMarginTop(40);
				  
			   }
			   seatingChart.getStudentsPanel().add(row2);
			   row2.add(c2);
		}
		grid.getContainer().add(factory.getCreateStudentForm());
		grid.getContainer().add(factory.getStudentActionModal());
		grid.getContainer().add(grid.getFab());

	}

	public void showEmpty() {
		HTMLPanel empty = new HTMLPanel("<h3>Your student list appears to be empty...</h3>"
				+ "<p>To manage you students just open the side menu"
				+ "and click on + button to add a new student </p>");
		empty.setStylePrimaryName("emptyClassroom");
		isGridEmpty = true;
		grid.getContainer().clear();
		grid.getContainer().add(empty);
		grid.getContainer().add(factory.getCreateStudentForm());
		grid.getContainer().add(factory.getStudentActionModal());
		grid.getContainer().add(grid.getFab());
	}

	private void getRosterStudentsListAndDrawGrid() {
		
		Ajax.get("/roster/" + factory.getCurrentRoster().getId() + "/student").done(new Function() {
			@Override
			public void f() {
				GWT.log(arguments(0).toString());
				if (arguments(0).toString().equals("[]")) {
					showEmpty();
				} else {
					JsArray<AppUserJson> students = JsonUtils.safeEval(arguments(0).toString());
					factory.setCurrentRosterStudentList(students);
					drawGrid(true);
				}
			}
		}).progress(new Function() {
			@Override
			public void f() {

			}

		}).fail(new Function() {
			@Override
			public void f() {
				Window.alert("Student Roster List could not be fetched from the server");
			}
		});
		
		
	}
	
	
	private void addNewStudentsToGrid(){
		
		MaterialMasonry addedStudentsMasonery = grid.getCreateStudentFrom().getAddedStudentsMasonery();
		
		
		//There is usually a Material Row, which counts for 1, this is a security check
		if(addedStudentsMasonery.getWidgetCount() == 1 || addedStudentsMasonery.getWidgetCount() == 0){
			return;
		}
			  if(isGridEmpty)
		         grid.getContainer().clear();
			  
		grid.getContainer().add(grid.getFab());
		grid.getContainer().add(grid.getCreateStudentFrom());
		grid.getContainer().add(grid.getStuModal());
		
		MaterialRow row = new MaterialRow();
		MaterialRow row2 = new MaterialRow();
		
		grid.getContainer().add(row);
		
		
		for(int i = 0; i < addedStudentsMasonery.getWidgetCount(); i++){
			Widget child = addedStudentsMasonery.getWidget(i);
			if(child.getClass().toString().equals("class net.videmantay.roster.views.student.StudentCard")){
				StudentCard card = (StudentCard) child;
				AppUserJson rosterStudent = JavaScriptObject.createObject().cast();
				rosterStudent.setName(card.getStudentNameLabel().getText());
				rosterStudent.setImageUrl(card.getStudentProfileImage().getUrl());
				rosterStudent.setId(card.getUserId());
				rosterStudent.setIncidentPointsAggregate(0);
				factory.addNewStudent(rosterStudent);  
				MaterialColumn c = new MaterialColumn();
				MaterialColumn c2 = new MaterialColumn();
				
				RosterStudentCard rsp = new RosterStudentCard(rosterStudent, this.factory);
				RosterStudentPanel seatingChartStudent = new RosterStudentPanel(rosterStudent);
				
				rsp.addStyleName("grid");
				c.add(rsp);
				row.add(c);
				
				//For seating chart grid
				c2.add(seatingChartStudent);
				
				   if( i != 0 && i % 3 == 0){
					   row2 = new MaterialRow();
					   //there is row above so make some space between the two
					   row2.setMarginTop(40);
				   }
				   row2.add(c2);
				   seatingChart.getStudentsPanel().add(row2);
				
			}
		}
	}

	private void getGradedWorkListAndDrawGrid() {
		Ajax.get("/gradedwork?rosterId=" + factory.getCurrentRoster().getId()).done(new Function() {
			@Override
			public void f() {
				GWT.log(arguments(0).toString());

				gradedWorkList = JsonUtils.safeEval(arguments(0).toString());

				List<GradedWorkJson> list = new ArrayList<GradedWorkJson>();
				for (int i = 0; i < gradedWorkList.length(); i++) {
					list.add(gradedWorkList.get(i));
				}
				assignementGrid.setRowCount(list.size());
				assignementGrid.setRowData(list);

			}
		}).progress(new Function() {
			@Override
			public void f() {

			}

		}).fail(new Function() {
			@Override
			public void f() {
				Window.alert("Student Roster List could not be fetched from the server");
			}
		});

	}

	@Override
	public void homeworkIconClickEvent() {
		dashboard.getHwIcon().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dashboard.getDisplay().checkHW();
				dashboard.setState(RosterDashboardPanel.State.HW);
				showDashboardDoneBar();
			}
		});

	}

	private void showDashboardDoneBar() {
		dashboard.getToolbar().getElement().getStyle().setDisplay(Style.Display.NONE);
		dashboard.getDoneToolbar().getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}

	private void showDashboardToolBar() {
		dashboard.getDoneToolbar().getElement().getStyle().setDisplay(Style.Display.NONE);
		dashboard.getToolbar().getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}

	@Override
	public void addStudentButtonClickEvent() {
		grid.getAddButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("Button clicked");
				grid.getCreateStudentFrom().show();
				// grid.getCreateStudentFrom().getPicker().setVisible(true);
			}

		});

	}
	
	private JsArray<RosterStudentJson> getCreateStudentFormData(){
		MaterialMasonry addedStudentsMasonery = grid.getCreateStudentFrom().getAddedStudentsMasonery();
		GWT.log("Entering getFormData" + addedStudentsMasonery.getWidgetCount());
		
		JsArray<RosterStudentJson> rosterStudentList = JavaScriptObject.createArray().cast();
		for(int i = 0; i < addedStudentsMasonery.getWidgetCount(); i++){
			Widget child = addedStudentsMasonery.getWidget(i);
			GWT.log(child.getClass().toString());
			if(child.getClass().toString().equals("class net.videmantay.roster.views.student.StudentCard")){
				StudentCard card = (StudentCard) child;
				RosterStudentJson rosterStudent = JavaScriptObject.createObject().cast();
				rosterStudent.setRosterId(String.valueOf(factory.getCurrentRoster().getId()));
				rosterStudent.setStudentId(String.valueOf(card.getUserId()));
				rosterStudentList.push(rosterStudent);  
			}
		}
		GWT.log(JsonUtils.stringify(rosterStudentList));
		return rosterStudentList;
	}

	@Override
	public void okButtonClickHandler() {
		grid.getCreateStudentFrom().getOkBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//Send data as string
				final JsArray<RosterStudentJson> studentIds = getCreateStudentFormData();
				
				
				if(grid.getCreateStudentFrom().getAddedStudentsMasonery().getWidgetCount() == 0){
					grid.getCreateStudentFrom().hide();		
				}else{
					
					GQuery.ajax("/roster/" + factory.getCurrentRoster().getId() + "/student",
							Ajax.createSettings().setData(studentIds).setType("POST").setDataType("json"))
							.done(new Function() {
								@Override
								public void f() {
									addNewStudentsToGrid();
									grid.getCreateStudentFrom().hide();
									isGridEmpty = false;
									MaterialLoader.showLoading(false);

								}
							}).progress(new Function() {
								@Override
								public void f() {
									MaterialLoader.showLoading(true);
								}
							}).fail(new Function() {
								@Override
								public void f() {
									MaterialLoader.showLoading(false);
									Window.alert("Error creating student");
								}
							});

				}

				
			}
		});

	}

	@Override
	public void cancelButtonClickHandler() {
		grid.getCreateStudentFrom().getCancelBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				factory.getCreateStudentForm().hide();
			}
		});
	}

	

	@Override
	public void tabsClickEvent() {
		dashboard.getCalTab().addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//Lazy loading calendar, only once, we do need to load it on each tab switch
				if(firstCalendarLoad){
				    dashboard.getCalendarContainer().add(factory.getGoogleCalendar());
				    firstCalendarLoad = false;
				}
			}
		}, ClickEvent.getType());

		dashboard.getDashboardTab().addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {


			}
		}, ClickEvent.getType());

		dashboard.getReportsTab().addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {


			}
		}, ClickEvent.getType());

	}

	@Override
	public void assignementGridFabClick() {
		gradedWorkMain.getFab().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				gradedWorkMain.getForm().show();

			}
		});

	}

	@Override
	public void gradedWorkFromOkButtonClickEvent() {
		gradedWorkMain.getForm().getOkBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GradedWorkJson newGradedWork = gradedWorkMain.getForm().getFormData();
				newGradedWork.setRosterId(factory.getCurrentRoster().getId());
				GWT.log("Object " + JsonUtils.stringify(newGradedWork));
				GQuery.ajax("/gradedwork",
						Ajax.createSettings().setData(newGradedWork).setType("POST").setDataType("json"))
						.done(new Function() {
							@Override
							public void f() {
								getGradedWorkListAndDrawGrid();
								gradedWorkMain.getForm().hide();
								MaterialLoader.showLoading(false);
							}
						}).progress(new Function() {
							@Override
							public void f() {
								MaterialLoader.showLoading(true);
							}
						}).fail(new Function() {
							@Override
							public void f() {
								MaterialLoader.showLoading(false);
								Window.alert("Error creating graded work");
							}
						});
			}
		});
	}

	@Override
	public void gradedWorkFromCancelButtonClickEvent() {
		gradedWorkMain.getForm().getCancelBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				gradedWorkMain.getForm().hide();

			}
		});
	}

	@Override
	public void addIncidentFABButtonClickEvent() {
		incidentMainPage.getAddIncidentFAB().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				incidentMainPage.getIncidentForm().show();
			}
		});

	}

	@Override
	public void doneButtonClickEvent() {
		incidentForm.getDoneBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final IncidentTypeJson newIncidentType = incidentForm.getFormData();		
				GQuery.ajax("/incidenttype",
						Ajax.createSettings().setData(newIncidentType).setType("POST").setDataType("json"))
						.done(new Function() {
							@Override
							public void f() {
								String id = arguments(0).toString();
								newIncidentType.setId(id);
								incidentMainPage.addIncidentType(newIncidentType);
								studentActionModal.addnewIncidentType(newIncidentType);
								incidentForm.hide();
								MaterialLoader.showLoading(false);
							}
						}).progress(new Function() {
							@Override
							public void f() {
								MaterialLoader.showLoading(true);
							}
						}).fail(new Function() {
							@Override
							public void f() {
								MaterialLoader.showLoading(false);
								Window.alert("failed to create incident");
							}
						});
			}
		});

	}

	@Override
	public void cancelButtonClickEvent() {
		incidentForm.getCancelBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				incidentMainPage.getIncidentForm().hide();
			}
		});
	}
	
	
	private void getIncidentsAndDraw() {
		Ajax.get("/roster/" + factory.getCurrentRoster().getId() + "/incident").done(new Function() {
			@Override
			public void f() {
				incidentsList = JsonUtils.safeEval(arguments(0).toString());
				incidentMainPage.getIncidentContainer().clear();
			
				for(int i = 0; i < incidentsList.length(); i++){
					IncidentJson incident = incidentsList.get(i);
					MaterialColumn col = new MaterialColumn();
					IncidentTypeJson incidentType = factory.findIncidentTypeById(incident.getIncidentTypeId());
					GWT.log("found inside draw " + incidentType.getImageUrl());
					StudentIncidentCard studentIncidentCard = new StudentIncidentCard(incident.getName(), incidentType.getImageUrl(), incidentType.getName());
					col.add(studentIncidentCard);
					incidentMainPage.getIncidentContainer().add(col);
				}
				
			}
		}).progress(new Function() {
			@Override
			public void f() {

			}
		}).fail(new Function() {
			@Override
			public void f() {
				Window.alert("Incident List could not be fetched from the server");
			}
		});

	}

	@Override
	public void manageClassTimeLinkClickEvent() {
		dashboard.getClassDropDownManageLink().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				goTo(new ClassTimePlace("classtime"));
				
			}
			
			
			
		});
	}
	
	private void getClassTimesForCurrentRosterAndDrawClassTimeGrid(){
		
		MaterialLoader.showLoading(true);
		Ajax.get("/roster/" + factory.getCurrentRoster().getId() + "/classtime").done(new Function() {
			@Override
			public void f() {
				GWT.log("received classtimes " + arguments(0).toString());
				currentRosterClassTimesList = JsonUtils.safeEval(arguments(0).toString());
				for(int i = 0; i < currentRosterClassTimesList.length(); i++){
					final ClassTimeJson classtime = currentRosterClassTimesList.get(i);
					MaterialLink link = new MaterialLink();
					link.setText(classtime.getStartTime() + " to " + classtime.getEndTime());
					link.addClickHandler(new ClickHandler(){
						@Override
						public void onClick(ClickEvent event) {
							factory.setSelectedClassTime(classtime);
							loadClassTimeConfiguration();
						}
					});
					dashboard.getClasstimeDropDown().add(link);
				}
				MaterialLoader.showLoading(false);
			}
		}).progress(new Function() {
			@Override
			public void f(){
				MaterialLoader.showLoading(true);
			}
		}).fail(new Function() {
			@Override
			public void f() {
				MaterialLoader.showLoading(false);
				Window.alert("Classtime List could not be fetched from the server");
			}
		});
		
		
	}
	
	
	private void loadClassTimeConfiguration(){
		
		Ajax.get("/roster/" + factory.getCurrentRoster().getId() + "/classtime/"+factory.getSelectedClassTime().getId()+"/config").done(new Function() {
			@Override
			public void f() {
				
				
				    
				
				
					}
				
		}).progress(new Function() {
			@Override
			public void f() {

			}

		}).fail(new Function() {
			@Override
			public void f() {
				Window.alert("Incident List could not be fetched from the server");
			}
		});
		
	}

	@Override
	public void classTimeAddButtonClickEvent() {
		classTimegrid.getCreateBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				factory.getClassTimeForm().show();
				
			}
		});
		
	}

	@Override
	public void createClassTimeFormSubmitButton() {
		factory.getClassTimeForm().getSubmitBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				final ClassTimeJson newClassTime = factory.getClassTimeForm().getFormData();
				newClassTime.setRosterId(String.valueOf(factory.getCurrentRoster().getId()));
				GQuery.ajax("/roster/" + factory.getCurrentRoster().getId() + "/classtime/",
						Ajax.createSettings().setData(newClassTime).setType("POST").setDataType("json"))
						.done(new Function() {
							@Override
							public void f() {
								GWT.log(arguments(0).toString());
								String newClassTimeId = arguments(0).toString();
								ClasstimeGridItem newClassTimeGridItem = new ClasstimeGridItem(newClassTime.getTitle(), newClassTime.getDescript(), newClassTimeId);
								classTimegrid.addItem(newClassTimeGridItem);
								currentRosterClassTimesList.push(newClassTime);
								MaterialLink newClassTimeLink = new MaterialLink();
								newClassTimeLink.setText(newClassTime.getTitle());
								newClassTimeLink.addClickHandler(new ClickHandler(){
									@Override
									public void onClick(ClickEvent event) {
										factory.setSelectedClassTime(newClassTime);
										loadClassTimeConfiguration();
									}
								});
								dashboard.getClasstimeDropDown().add(newClassTimeLink);
								factory.getClassTimeForm().hide();
								MaterialToast.fireToast("ClassTime created");
								MaterialLoader.showLoading(false);
							}
						}).progress(new Function() {
							@Override
							public void f() {
								MaterialLoader.showLoading(true);
							}
						}).fail(new Function() {
							@Override
							public void f() {
								MaterialLoader.showLoading(false);
								Window.alert("could not get classtimes from the server");
							}
						});
				
			}
			
		});
		
	}

	@Override
	public void createClassTimeFormCancelButton() {
		factory.getClassTimeForm().getCancelBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				factory.getClassTimeForm().hide();
				
			}
			
		});
		
	}
	
	
	private void drawClassTimeGrid(){
		classTimegrid.getContainer().clear();
		for(int i = 0; i < currentRosterClassTimesList.length(); i++){
			ClassTimeJson classtime = currentRosterClassTimesList.get(i);
			ClasstimeGridItem item = new ClasstimeGridItem(classtime.getTitle(), classtime.getDescript(), classtime.getId());
			classTimegrid.addItem(item);
		}
		
		
	}

	@Override
	public void seatingChartEditClickEvent() {
		dashboard.getSeatingChartEditIcon().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				dashboard.showDoneBar();
				factory.setEditMode(true);
				enableEditingInSeatingChart();
				
			}
		});
		
	}

	@Override
	public void barDoneButtonClickEvent() {
		dashboard.getDoneBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				//disableDragAndDropAndRotationInFloorPlan();
				disableEditingInSeatingChart();
				dashboard.showToolBar();
				factory.setEditMode(false);
			}
		});
		
	}

	@Override
	public void barCancelButtonClickEvent() {
		dashboard.getCancelBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				disableEditingInSeatingChart();
				dashboard.showToolBar();
				factory.setEditMode(false);
			}
		});
		
	}

	
	
	public void resetRosterDataLists(){
		JsArray<AppUserJson> emptyStudents = JavaScriptObject.createObject().cast();
		factory.setCurrentRosterStudentList(emptyStudents);
		gradedWorkList = JavaScriptObject.createObject().cast();
		incidentsList = JavaScriptObject.createObject().cast();
		currentRosterClassTimesList = JavaScriptObject.createObject().cast();
		
	}
	
	
	public AppUserJson findStudentById(String id){
		for(int i = 0; i < factory.getCurrentRosterStudentList().length(); i++){
			AppUserJson appUser = factory.getCurrentRosterStudentList().get(i);
			if(appUser.getId().compareTo(id) == 0){
				return appUser;
			}
		}
		return null;
	}

	@Override
	public void studentActionModalOkButtonClickEvent() {
		factory.getStudentActionModal().getStudentActionModalOkButton().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				factory.getStudentActionModal().hide();
			}
		});
		
	}


	@Override
	public void incidentFormSelectedIconClickEvent() {	
		iconInput.getSelectedIconContainer().addDomHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(isIconsInputDisplayed){
					isIconsInputDisplayed = false;
				    iconInput.getIconsContainer().getElement().removeClassName("iconFormInputContainer");
				    iconInput.getIconsContainer().getElement().getStyle().setDisplay(Display.NONE);
				}else{
					isIconsInputDisplayed = true;
					iconInput.getIconsContainer().getElement().addClassName("iconFormInputContainer");
					iconInput.getIconsContainer().getElement().getStyle().setProperty("display", "flex");
				}
			}
		}, ClickEvent.getType());
		
		
		
	}

	@Override
	public void removeSeatingChartActionButtonClickEvent() {
		seatingChart.getRemoveButton().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(SelectionManager.isSelectionActive() && factory.isEditMode()){
					Element selectedElement = SelectionManager.getSelection();
					SelectionManager.unSelect(selectedElement);
					NodeList<Element> studentDraggables = $(selectedElement).find(".studentDraggable").get();
					
					   for(int i = 0; i < studentDraggables.getLength(); i++){
						   Element studentDraggable = studentDraggables.getItem(i);
						   String studentId = studentDraggable.getAttribute("studentid");
							$("#"+studentId+" .rosterPanelSeatedDiv").css("visibility", "hidden");
					   }
					
					
					selectedElement.removeFromParent();
				}
				
			}
			
			
		});
		
	}

	@Override
	public void rollIconClick() {
		dashboard.getRollIcon().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				//changeHash();
				dashboard.showRollBar();
				factory.setRollMode(true);
				$(".attendenceBadge").css("visibility", "visible");
				$(".pointsBadge").css("visibility", "hidden");
				
				
			}
		});
		
	}

	@Override
	public void saveRollButtonClick() {
		dashboard.getSaveRollBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				dashboard.showToolBar();
				factory.setRollMode(false);
				$(".attendenceBadge").css("visibility", "hidden");
				$(".pointsBadge").css("visibility", "visible");
			}
		});
		
		
	}

	@Override
	public void cancelRollButtonClick() {
		dashboard.getCancelRollBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				dashboard.showToolBar();
				factory.setRollMode(false);
				$(".attendenceBadge").css("visibility", "hidden");
				$(".pointsBadge").css("visibility", "visible");
			}
		});
	}
	
	
	private void disableEditingInSeatingChart(){
		$("#editing").css("visibility", "visible");
	}
	
    private void enableEditingInSeatingChart(){
		$("#editing").css("visibility", "hidden");
	}

	@Override
	public void editingDivClickEvent() {
		Event.sinkEvents(seatingChart.getEditingDiv(), Event.ONCLICK);
		Event.setEventListener(seatingChart.getEditingDiv(), new EventListener(){
			@Override
			public void onBrowserEvent(Event event) {
				MaterialToast.fireToast("You need to activate edit mode before editing the floor plan", 2000);
			}
		});
		
	}
	
	
	private void WindowResizeHandler(){
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if(Window.getClientWidth() < 768){
				dashboard.getGridSwitch().setValue(false);
				}
			}
		});
		
	}
	


	@Override
	public void undoButtonClickEvent() {
		dashboard.getUndoBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				factory.getUndoRedoManager().undoLastAction();
			}
		});
	}

	@Override
	public void redoButtonClickEvent() {
		dashboard.getRedoBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				factory.getUndoRedoManager().redoLastAction();
			}
		});
	}
	
}