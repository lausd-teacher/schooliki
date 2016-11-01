package net.videmantay.roster.activities;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.places.CalendarPlace;
import net.videmantay.roster.places.ClassRoomPlace;
import net.videmantay.roster.places.LessonsPlace;
import net.videmantay.roster.places.LibraryPlace;
import net.videmantay.roster.places.RosterHomePlace;
import net.videmantay.roster.places.SettingsPlace;
import net.videmantay.roster.places.UserProfilePlace;
import net.videmantay.roster.views.AppLayout;
import net.videmantay.roster.views.RosterDisplay;
import net.videmantay.roster.views.RosterForm;
import net.videmantay.roster.views.RosterGrid;
import net.videmantay.roster.views.RosterPanel;
import net.videmantay.roster.views.UserProfilePage;
import net.videmantay.roster.views.components.MainRosterNavBar;
import net.videmantay.roster.views.components.MainRosterSideNav;
import net.videmantay.shared.util.GoogleJs;

public class RosterDisplayActivity extends AbstractActivity
		implements RosterDisplay.Presenter, RosterForm.Presenter, RosterPanel.Presenter, MainRosterSideNav.Presenter {

	ClientFactory factory;
	RosterDisplay rosterDisplay;
	RosterForm rosterForm;
	RosterGrid grid;
	JsArray<RosterJson> rosterList = JavaScriptObject.createArray().cast();
	RosterJson data = JavaScriptObject.createObject().cast();
	Place currentPlace;
	AppLayout appPanel;
	MainRosterSideNav mainRosterSideNav;
	MainRosterNavBar mainRosterNavBar;
	UserProfilePage profilePage;

	public RosterDisplayActivity(ClientFactory factory, Place currentPlace) {
		this.factory = factory;
		this.rosterDisplay = factory.getRosterDisplay();
		this.rosterForm = rosterDisplay.getRosterForm();
		this.grid = rosterDisplay.getRosterGrid();
		this.currentPlace = currentPlace;
		this.appPanel = factory.getAppPanel();
		this.mainRosterNavBar = factory.gerMainRosterNavBar();
		this.mainRosterSideNav = factory.getMainRosterSideNav();
		this.profilePage = factory.getUserProfilePage();
		initializeEvents();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		//Adding links one by one, as they cannot be added as whole
		appPanel.getSideNav().clear();
		appPanel.getSideNav().add(factory.userProfile());
		appPanel.getSideNav().add(mainRosterSideNav.getRosterLink());
		appPanel.getSideNav().add(mainRosterSideNav.getCalendarLink());
		appPanel.getSideNav().add(mainRosterSideNav.getLibraryLink());
		appPanel.getSideNav().add(mainRosterSideNav.getLessonsLink());
		appPanel.getSideNav().add(mainRosterSideNav.getSettingsLink());
		appPanel.getSideNav().add(mainRosterSideNav.getProfileLink());
		appPanel.getSideNav().add(mainRosterSideNav.getLogoutLink());
		
		//Adding Nav Bar
		appPanel.getNavBartitle().setText(factory.getCurrentUserName());
		appPanel.getNavBarContainer().clear();
		appPanel.getNavBarContainer().add(mainRosterNavBar.getCalendarTooltip());
		appPanel.getNavBarContainer().add(mainRosterNavBar.getTodoTooltip());
		appPanel.getNavBarContainer().add(mainRosterNavBar.getNotificationTooltip());
		
		appPanel.getMainPanel().clear();
		
		hideSideNav();

		if (currentPlace instanceof RosterHomePlace) {
			appPanel.getMainPanel().add(rosterDisplay);
			getRostersDataAndDrawGrid();
		} else if (currentPlace instanceof LessonsPlace) {
			appPanel.getMainPanel().add(new Label("Lessons section is not implemented yet"));
		} else if (currentPlace instanceof LibraryPlace) {
			appPanel.getMainPanel().add(new Label("Library section is not implemented yet"));
		} else if (currentPlace instanceof CalendarPlace) {
			appPanel.getMainPanel().add(factory.getGoogleCalendar());
		}else if (currentPlace instanceof SettingsPlace) {
			appPanel.getMainPanel().add(new Label("Settings section is not implemented yet"));
		}else if (currentPlace instanceof UserProfilePlace) {
			appPanel.getMainPanel().add(profilePage);
			
			
		}
		panel.setWidget(appPanel);
		
	}

	private void initializeEvents() {
		addRosterClickEvent();
		submitButtonClick();
		cancelButtonClick();
		rosterLinkClick();
		calendarLinkClick();
		libraryLinkClick();
		lessonsLinkClick();
		settingsLinkClick();
		profileLinkClick();
		appLogoutLinkClick();
	}

	public void getRostersDataAndDrawGrid() {

		Ajax.get("/roster").done(new Function() {
			@Override
			public void f() {
				GWT.log(this.arguments(0).toString());
				rosterList = JsonUtils.safeEval(this.arguments(0).toString()).cast();
				GWT.log("success");
				drawGrid();
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
				Window.alert("Error connecting to the Server, Please try again later");
			}
		});
	}

	@Override
	public void addRosterClickEvent() {
		rosterDisplay.getFab().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rosterDisplay.getFab().setVisible(false);
				rosterDisplay.getRosterGrid().setVisible(false);
				rosterDisplay.getRosterForm().setVisible(true);
			}
		});

	}

	@Override
	public void submitButtonClick() {
		rosterForm.getSubmitBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final RosterJson formData = rosterForm.collectFormData();
				GQuery.ajax("/roster", Ajax.createSettings().setData(formData).setType("POST").setDataType("json"))
						.done(new Function() {
							@Override
							public void f() {
								formData.setId(Long.valueOf(this.arguments(0).toString()));
								rosterList.push(formData);
								drawGrid();
								MaterialLoader.showLoading(false);
								rosterForm.setVisible(false);
								rosterDisplay.getRosterGrid().setVisible(true);
								rosterDisplay.getFab().setVisible(true);
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
								Window.alert("Error connecting to the Server, Please try again later");
							}
						});
			}

		});

	}

	@Override
	public void cancelButtonClick() {
		rosterForm.getCancelBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//getRostersDataAndDrawGrid();
				rosterForm.setVisible(false);
				rosterDisplay.getRosterGrid().setVisible(true);
				rosterDisplay.getFab().setVisible(true);
				
			}
		});

	}

	@Override
	public void cardClickEvent(MaterialCard rostercard, final RosterJson roster) {
		$(rostercard).click(new Function() {
			@Override
			public boolean f(Event e) {
				GWT.log("card clicker" + roster.getId().toString());
				factory.setCurrentRoster(roster);
				return true;
			}
		});

	}

	public void drawGrid() {

		if (rosterList == null || rosterList.length() <= 0) {
			grid.showEmptyList();
		} else {
			grid.clear();
			MaterialRow row = new MaterialRow();
			for (int i = 0; i < rosterList.length(); i++) {
				final RosterJson currentRoster = rosterList.get(i);
				MaterialColumn col = new MaterialColumn(12, 6, 4);
				final RosterPanel panel = new RosterPanel(){
					@Override
					public void onLoad(){
						$(this.getRosterCard()).click(new Function() {
							@Override
							public boolean f(Event e) {
								GWT.log("card clicker" + currentRoster.getId());
								factory.setCurrentRoster(currentRoster);
								goToClassRoom(currentRoster.getId());
								
								return true;
							}
						});						
					}
				};
				panel.setData(currentRoster);
				col.add(panel);
				row.add(col);
				grid.add(row);

			}
		}
	}

	public void goToClassRoom(Long id) {
		String token = String.valueOf(id);
		goTo(new ClassRoomPlace(token));
	}

	public void goTo(Place place) {
		factory.getPlaceController().goTo(place);
	}

	@Override
	public void rosterLinkClick() {
		mainRosterSideNav.getRosterLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new RosterHomePlace("rosters"));
			}

		});

	}

	@Override
	public void calendarLinkClick() {
		mainRosterSideNav.getCalendarLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new CalendarPlace("calendar"));
			}

		});
	}

	@Override
	public void libraryLinkClick() {
		mainRosterSideNav.getLibraryLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new LibraryPlace("library"));
			}
		});
	}

	@Override
	public void lessonsLinkClick() {
		mainRosterSideNav.getLessonsLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new LessonsPlace("lessons"));
			}
		});
	}
	
	@Override
	public void settingsLinkClick() {
		mainRosterSideNav.getSettingsLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goTo(new SettingsPlace("settings"));
			}
		});
		
	}
	
	@Override
	public void profileLinkClick() {
		mainRosterSideNav.getProfileLink().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				goTo(new UserProfilePlace("profile"));
				
			}
		});
	}


	public void setPlace(Place place) {
		currentPlace = place;
		hideSideNav();
		appPanel.getMainPanel().clear();
		
		appPanel.getSideNav().clear();
		appPanel.getSideNav().add(factory.userProfile());
		appPanel.getSideNav().add(mainRosterSideNav.getRosterLink());
		appPanel.getSideNav().add(mainRosterSideNav.getCalendarLink());
		appPanel.getSideNav().add(mainRosterSideNav.getLibraryLink());
		appPanel.getSideNav().add(mainRosterSideNav.getLessonsLink());
		appPanel.getSideNav().add(mainRosterSideNav.getSettingsLink());
		appPanel.getSideNav().add(mainRosterSideNav.getProfileLink());
		appPanel.getSideNav().add(mainRosterSideNav.getLogoutLink());
		
		
		//Adding Nav Bar
		appPanel.getNavBartitle().setText(factory.getCurrentUserName());
		appPanel.getNavBarContainer().clear();
		appPanel.getNavBarContainer().add(mainRosterNavBar.getCalendarTooltip());
		appPanel.getNavBarContainer().add(mainRosterNavBar.getTodoTooltip());
		appPanel.getNavBarContainer().add(mainRosterNavBar.getNotificationTooltip());

		
		if (currentPlace instanceof RosterHomePlace) {
			
			appPanel.getMainPanel().add(rosterDisplay);
			//No need to refresh the list again
			//getRostersDataAndDrawGrid();
		} else if (currentPlace instanceof LessonsPlace) {
			appPanel.getMainPanel().add(new Label("Lessons section is not implemented yet"));
		} else if (currentPlace instanceof LibraryPlace) {
			appPanel.getMainPanel().add(new Label("Library section is not implemented yet"));
		} else if (currentPlace instanceof CalendarPlace) {
			appPanel.getMainPanel().add(factory.getGoogleCalendar());
		}else if (currentPlace instanceof SettingsPlace) {
			appPanel.getMainPanel().add(new Label("Settings section is not implemented yet"));
		}else if (currentPlace instanceof UserProfilePlace) {
			appPanel.getMainPanel().add(profilePage);
		}
		
		
	}
	
	private void hideSideNav(){
		
		new Timer(){
			@Override
			public void run() {
				appPanel.getSideNav().hide();
			}}.schedule(250);
	}

	@Override
	public void appLogoutLinkClick() {
		mainRosterSideNav.getLogoutLink().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				GoogleJs.logout();
				
			}
		});
	}

	
	
	
	


}
