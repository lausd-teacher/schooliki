package net.videmantay.roster.activities;

import static com.google.gwt.query.client.GQuery.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SingleSelectionModel;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.json.GradedWorkJson;
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
import net.videmantay.roster.views.ClassroomGrid;
import net.videmantay.roster.views.RosterDashboardPanel;
import net.videmantay.roster.views.RosterStudentPanel;
import net.videmantay.roster.views.assignment.AssignmentGrid;
import net.videmantay.roster.views.assignment.EmptyAssignmentGrid;
import net.videmantay.roster.views.assignment.GradedWorkMain;
import net.videmantay.roster.views.classtime.SeatingChartPanel;
import net.videmantay.roster.views.components.ClassRoomSideNav;
import net.videmantay.roster.views.student.CreateStudentForm;
import net.videmantay.roster.views.student.FistNameCompare;
import net.videmantay.roster.views.student.LastNameCompare;
import net.videmantay.student.json.RosterStudentJson;

public class ClassRoomActivity extends AbstractActivity implements ClassRoomSideNav.Presenter,
		RosterDashboardPanel.Presenter, ClassroomGrid.Presenter, CreateStudentForm.Presenter {

	ClientFactory factory;

	RosterDashboardPanel dashboard;
	Place currentPlace;
	AppLayout appPanel;
	ClassRoomSideNav classRoomSideNav;
	GradedWorkMain gradedWorkMain;
	ClassroomGrid grid;
	SeatingChartPanel seatingChart;
	JsArray<RosterStudentJson> students = JavaScriptObject.createObject().cast();

	public ClassRoomActivity(ClientFactory factory, Place place) {
		this.factory = factory;
		this.dashboard = factory.getRosterDashBoard();
		this.appPanel = factory.getAppPanel();
		this.currentPlace = place;
		this.dashboard = factory.getRosterDashBoard();
		this.classRoomSideNav = factory.getClassRoomSideNav();
		this.grid = factory.getClassRoomGrid();
		this.gradedWorkMain = factory.getGradedWorkMain();
		this.seatingChart = factory.getSettingChartPanel();
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
		    getRosterStudentsListAndDrawGrid();
			dashboard.setDisplay(grid);
			appPanel.getMainPanel().add(dashboard);
		} else if (currentPlace instanceof AssignementPlace) {
			AssignmentGrid grid = consctructAssignementGridTable();
			gradedWorkMain.setAssignementGrid(grid);
			appPanel.getMainPanel().add(gradedWorkMain);
		} else if (currentPlace instanceof LessonPlanPlace) {
			appPanel.getMainPanel().add(new Label("LessonPlan view is not implemented yet"));
		} else if (currentPlace instanceof IncidentPlace) {
			appPanel.getMainPanel().add(new Label("Incident view is under construction"));
		} else if (currentPlace instanceof GoalPlace) {
			appPanel.getMainPanel().add(new Label("Goal view is under construction"));
		} else if (currentPlace instanceof JobPlace) {
			appPanel.getMainPanel().add(new Label("Job view is under construction"));
		} else if (currentPlace instanceof BookPlace) {
			appPanel.getMainPanel().add(new Label("BookPlace view is not implemented yet"));
		} else if (currentPlace instanceof ClassTimePlace) {
			appPanel.getMainPanel().add(new Label("ClassTime view is under construction"));
		} else if (currentPlace instanceof FormPlace) {
			appPanel.getMainPanel().add(new Label("FormPlace view is under construction"));
		}

		panel.setWidget(appPanel);

	}

	private void hideSideNav() {
		appPanel.getSideNav().hide();
	}

	private void goTo(Place place) {
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
				goTo(new BookPlace("job"));

			}

		});

	}

	@Override
	public void classTimeClickEvent() {
		classRoomSideNav.getClassTimeLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new ClassTimePlace("job"));
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

		AsyncDataProvider<GradedWorkJson> providesKey = new AsyncDataProvider<GradedWorkJson>() {

			@Override
			protected void onRangeChanged(final HasData<GradedWorkJson> display) {
				Ajax.get("/gradedwork?=rosterId" + factory.getCurrentRoster().getId()).done(new Function() {
					@Override
					public void f() {
						JsArray<GradedWorkJson> list = JsonUtils.safeEval((String) this.arguments(0)).cast();
						ArrayList<GradedWorkJson> data = new ArrayList<GradedWorkJson>();
						for (int i = 0; i < list.length(); i++) {
							data.add(list.get(i));
						}
						display.setRowData(0, data);
						display.setRowCount(data.size());
					}
				}).fail(new Function() {
					@Override
					public void f() {
						Window.alert("Unable to fetch graded work list from the server");
					}
				});

			}

			@Override
			public Long getKey(GradedWorkJson value) {
				return value.getId();
			}

		};

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
				return object.getGradedWorkType().name();
			}
		};

		TextColumn<GradedWorkJson> subjectCol = new TextColumn<GradedWorkJson>() {

			@Override
			public String getValue(GradedWorkJson object) {
				return object.getSubject().name();
			}
		};
		TextColumn<GradedWorkJson> assignedToCol = new TextColumn<GradedWorkJson>() {

			@Override
			public String getValue(GradedWorkJson object) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < object.getAssignedTo().length(); i++) {
					sb.append(object.getAssignedTo().get(i));
					if (i != object.getAssignedTo().length() - 1) {
						sb.append(", ");
					}
				}
				return sb.toString();
			}
		};

		TextColumn<GradedWorkJson> dueDateCol = new TextColumn<GradedWorkJson>() {

			@Override
			public String getValue(GradedWorkJson object) {
				return object.getSubject().name();
			}
		};

		TextColumn<GradedWorkJson> assignedDateCol = new TextColumn<GradedWorkJson>() {

			@Override
			public String getValue(GradedWorkJson object) {
				return object.getSubject().name();
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
				SafeHtml html;
				if (object.isFinishedGrading()) {
					html = SafeHtmlUtils.fromString("<i class='material-icons'>done</i>");
				} else {
					html = SafeHtmlUtils.fromString("<i></i>");
				}
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
		// this.addColumn(descriptCol, SafeHtmlUtils.fromTrustedString("<span
		// class='gradedWork-grid-header'>Summary</span>"));
		assignementGrid.addColumn(assignedDateCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Assigned<br/> Date</span>"));
		assignementGrid.addColumn(assignedToCol,
				SafeHtmlUtils.fromTrustedString("<span class='gradedWork-grid-header'>Students</span>"));
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

		if (place instanceof ClassRoomPlace) {
			getRosterStudentsListAndDrawGrid();
			appPanel.getMainPanel().add(dashboard);
		} else if (place instanceof AssignementPlace) {
			AssignmentGrid grid = consctructAssignementGridTable();
			gradedWorkMain.setAssignementGrid(grid);
			appPanel.getMainPanel().add(gradedWorkMain);
		} else if (place instanceof LessonPlanPlace) {
			appPanel.getMainPanel().add(new Label("LessonPlan view is not implemented yet"));
		} else if (place instanceof IncidentPlace) {
			appPanel.getMainPanel().add(new Label("LessonPlan view is not implemented yet"));
		} else if (place instanceof GoalPlace) {
			appPanel.getMainPanel().add(new Label("LessonPlan view is not implemented yet"));
		} else if (place instanceof JobPlace) {
			appPanel.getMainPanel().add(new Label("LessonPlan view is not implemented yet"));
		} else if (place instanceof BookPlace) {
			appPanel.getMainPanel().add(new Label("BookPlace view is not implemented yet"));
		} else if (place instanceof ClassTimePlace) {
			appPanel.getMainPanel().add(new Label("LessonPlan view is not implemented yet"));
		} else if (place instanceof FormPlace) {
			appPanel.getMainPanel().add(new Label("FormPlace view is not implemented yet"));
		}
		
		currentPlace = place;

	}

	private void initializeEvents() {

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
		pickerButtonClick();

	}

	@Override
	public void gridSwitchClickEvent() {

		dashboard.getGridSwitch().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (dashboard.getViewType() == RosterDashboardPanel.View.GRID) {
					dashboard.setViewType(RosterDashboardPanel.View.CHART);
					dashboard.setDisplay(seatingChart);
				} else {
					dashboard.setViewType(RosterDashboardPanel.View.GRID);
					dashboard.setDisplay(grid);
				}
			}
		});

	}

	private void drawGrid(boolean sortbyFirstName) {
        
		//dashboard.getTab1Main().clear();
		MaterialRow row = new MaterialRow();
		grid.clear();
		grid.add(row);
		MaterialColumn c;
		RosterStudentPanel rsp;

		List<RosterStudentJson> studentList = new ArrayList<RosterStudentJson>();
		GWT.log("before" + students.toString());
	
			for (int i = 0; i < students.length(); i++) {
				studentList.add(students.get(i));
			}

			if (sortbyFirstName) {
				Collections.sort(studentList, new FistNameCompare());
			} else {
				Collections.sort(studentList, new LastNameCompare());
			}

			int i = 0;

			do {
				c = new MaterialColumn();
				rsp = new RosterStudentPanel(students.get(i));
				rsp.addStyleName("grid");
				c.add(rsp);
				++i;
				row.add(c);
			} while (i < students.length());

			

		
		
		grid.add(factory.getCreateStudentForm());
		grid.add(grid.getFab());

	}



	public void showEmpty() {
		HTMLPanel empty = new HTMLPanel("<h3>Your student list appears to be empty...</h3>"
				+ "<p>To manage you students just open the side menu"
				+ "and click on + button to add a new student </p>");
		empty.setStylePrimaryName("emptyClassroom");
        grid.clear();
		grid.add(empty);
		grid.add(grid.getFab());
	}

	private void getRosterStudentsListAndDrawGrid() {
		Ajax.get("/roster/" + factory.getCurrentRoster().getId() + "/student").done(new Function() {
			@Override
			public void f() {
				GWT.log(arguments(0).toString());
				if(arguments(0).toString().equals("[]")){
					showEmpty();
				}else{
				    students = JsonUtils.safeEval(arguments(0).toString());
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
				grid.getCreateStudentFrom().show();
			}

		});

	}

	@Override
	public void okButtonClickHandler() {

		grid.getCreateStudentFrom().getOkBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final RosterStudentJson newStudent = grid.getCreateStudentFrom().getFormData();

				GQuery.ajax("/roster/" + factory.getCurrentRoster().getId() + "/student",
						Ajax.createSettings().setData(newStudent).setType("POST").setDataType("json"))
						.done(new Function() {
							@Override
							public void f() {
								console.log("create student form done return : " + this.getArgument(0));
								grid.getCreateStudentFrom().hide();
								//GWT.log(message);
								Long id = Long.parseLong(this.getArgument(0).toString());
								newStudent.setId(id);
								students.push(newStudent);
								drawGrid(true);
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
		});

	}

	@Override
	public void cancelButtonClickHandler() {
		grid.getCreateStudentFrom().getCancelBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				grid.getCreateStudentFrom().hide();
			}
		});
	}

	@Override
	public void pickerButtonClick() {
		grid.getCreateStudentFrom().getPickerButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				grid.getCreateStudentFrom().hide();
				grid.getCreateStudentFrom().getPicker().setVisible(true);
			}
		});
	}

}
