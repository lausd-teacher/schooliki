package net.videmantay.admin;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import net.videmantay.admin.json.AppUserJson;
import net.videmantay.admin.views.AdminGrid;
import net.videmantay.admin.views.AppLayout;
import net.videmantay.admin.views.AppUserDataTable;
import net.videmantay.admin.views.AppUserDeleteModal;
import net.videmantay.admin.views.AppUserForm;
import net.videmantay.admin.views.components.AdminSideNav;

public class ClientFactoryImpl implements ClientFactory{
	
	EventBus eventBus = new SimpleEventBus();
	PlaceController placeController = new PlaceController(eventBus);
	AppLayout appLayout = new AppLayout();
	AdminSideNav adminSideNav = new AdminSideNav();
	AppUserForm form = new AppUserForm();
	AppUserDeleteModal deleteModal = new AppUserDeleteModal();
	AppUserDataTable dataTable = new AppUserDataTable(deleteModal, this);
	AdminGrid grid = new AdminGrid(dataTable, form, deleteModal);
	AppUserJson currentUser = JavaScriptObject.createObject().cast();
	

	@Override
	public AppLayout getAppLayout() {
		return appLayout;
	}

	@Override
	public AdminSideNav getAdminSideNav() {
		return adminSideNav;
	}

	@Override
	public AppUserForm getAppUserForm() {
		return form;
	}

	@Override
	public AppUserDataTable getAppUserDataTable() {
		return dataTable;
	}

	@Override
	public AdminGrid getAdminGrid() {
		return grid;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public AppUserDeleteModal getAppUserDeleteModal() {
		return deleteModal;
	}

	@Override
	public AppUserJson getCurrentSelectedUser() {
		return currentUser;
	}

	@Override
	public void setCurrentSelectedUser(AppUserJson currentSelectedUser) {
		this.currentUser = currentSelectedUser;
	}

}
