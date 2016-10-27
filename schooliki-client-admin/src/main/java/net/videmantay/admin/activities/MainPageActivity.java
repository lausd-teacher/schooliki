package net.videmantay.admin.activities;

import java.util.Arrays;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import gwt.material.design.client.ui.MaterialLoader;
import net.videmantay.admin.ClientFactory;
import net.videmantay.admin.json.AppUserJson;
import net.videmantay.admin.views.AdminGrid;
import net.videmantay.admin.views.AppLayout;
import net.videmantay.admin.views.AppUserDeleteModal;
import net.videmantay.admin.views.AppUserForm;
import net.videmantay.admin.views.components.AdminSideNav;

public class MainPageActivity extends AbstractActivity implements AppUserForm.Presenter, AdminGrid.Presenter, AppUserDeleteModal.Presenter {
	
	
	ClientFactory factory;
	Place currentPlace;
	AppLayout layout;
	AdminSideNav sideNav;
	AdminGrid grid;
	AppUserDeleteModal deleteModal;
	
	public MainPageActivity(ClientFactory factory, Place currentPlace){
		this.factory = factory;
		this.currentPlace = currentPlace;
		this.layout = factory.getAppLayout();
		this.sideNav = factory.getAdminSideNav();
		this.grid = factory.getAdminGrid();
		this.deleteModal = factory.getAppUserDeleteModal();
		initializeEvents();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		layout.getSideNav().add(sideNav.getAppsLink());
		layout.getSideNav().add(sideNav.getFavoriteLink());
		layout.getSideNav().add(sideNav.getNavigationLink());
		layout.getSideNav().add(sideNav.getNotificationsLink());
		layout.getSideNav().add(sideNav.getStarterLink());
		
		layout.getMainPanel().add(grid);
		
		
		
		panel.setWidget(layout);
	}
	
	
	public void setPlace(Place place){
		currentPlace = place;
		
		//Not needed for now because we have one place only
//		layout.getSideNav().add(sideNav.getAppsLink());
//		layout.getSideNav().add(sideNav.getFavoriteLink());
//		layout.getSideNav().add(sideNav.getNavigationLink());
//		layout.getSideNav().add(sideNav.getNotificationsLink());
//		layout.getSideNav().add(sideNav.getStarterLink());
//		
//		
//		layout.getMainPanel().add(grid);
		
		
	}
	
	
	
	public void initializeEvents(){
		
		saveButtonClickEvent();
		cancelButtonClickEvent();
		fabButtonClickEvent();
		cancelDeleteClickEvent();
		confirmDeleteClickEvent();
	}

	@Override
	public void saveButtonClickEvent() {
		grid.getForm().getSubmitBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				
				//Ajax call
				
				// + 
				final AppUserJson newAppUser = grid.getForm().collectFormData();
				GQuery.ajax("/appuser", Ajax.createSettings().setData(newAppUser).setType("POST").setDataType("json"))
				.done(new Function() {
					@Override
					public void f() {
						GWT.log(this.arguments(0).toString());
						//Refresh grid
						newAppUser.setId(Long.parseLong(this.arguments(0).toString()));
						int rowCount = factory.getAppUserDataTable().getRowCount();
						factory.getAppUserDataTable().setRowData(rowCount, Arrays.asList(newAppUser));
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
				
				grid.getForm().hide();
			}
		});
		
	}

	@Override
	public void cancelButtonClickEvent() {
		grid.getForm().getCancelBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				grid.getForm().hide();
				
			}
		});
		
	}

	@Override
	public void fabButtonClickEvent() {
		grid.getFab().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				grid.getForm().show();
				
			}
		});
		
	}

	@Override
	public void confirmDeleteClickEvent() {
		final AppUserJson userToBeDesactivated = factory.getCurrentSelectedUser();
//		deleteModal.getConfirmDeleteBtn().addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//				GQuery.ajax("/appuser/"+ userToBeDesactivated.getId(), Ajax.createSettings().setType("DELETE").setDataType("json"))
//				.done(new Function() {
//					@Override
//					public void f() {
//						GWT.log(this.arguments(0).toString());
//						//Refresh grid
//						
//						MaterialLoader.showLoading(false);
//
//					}
//				}).progress(new Function() {
//					@Override
//					public void f() {
//						MaterialLoader.showLoading(true);
//					}
//				}).fail(new Function() {
//					@Override
//					public void f() {
//						MaterialLoader.showLoading(false);
//						Window.alert("Error connecting to the Server, Please try again later");
//					}
//				});
//				
//				
//			}
//		});
		
	}

	@Override
	public void cancelDeleteClickEvent() {
		deleteModal.getCancelDeleteBtn().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {	
				deleteModal.close();
			}
		});
		
	}
}
