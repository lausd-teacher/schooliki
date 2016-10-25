package net.videmantay.admin;


import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

import net.videmantay.admin.views.AdminGrid;
import net.videmantay.admin.views.AppLayout;
import net.videmantay.admin.views.AppUserDataTable;
import net.videmantay.admin.views.AppUserForm;
import net.videmantay.admin.views.components.AdminSideNav;

public interface ClientFactory {
	AppLayout getAppLayout();
	AdminSideNav getAdminSideNav();
	AppUserForm getAppUserForm();
	AppUserDataTable getAppUserDataTable();
	AdminGrid getAdminGrid();
	PlaceController getPlaceController();
	EventBus getEventBus();
}
