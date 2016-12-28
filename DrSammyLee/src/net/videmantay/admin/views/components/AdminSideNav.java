package net.videmantay.admin.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;

public class AdminSideNav extends Composite {

	private static AdminSideNavUiBinder uiBinder = GWT.create(AdminSideNavUiBinder.class);

	interface AdminSideNavUiBinder extends UiBinder<Widget, AdminSideNav> {
	}
	
	@UiField
	MaterialLink logoutLink;
	
	@UiField
	MaterialLink appsLink;
	
	@UiField
	MaterialLink notificationsLink;
	
	@UiField
	MaterialLink navigationLink;
	
	@UiField
	MaterialLink favoriteLink;

	public AdminSideNav() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public MaterialLink getLogoutLink() {
		return this.logoutLink;
	}

	public MaterialLink getAppsLink() {
		return this.appsLink;
	}

	public MaterialLink getNotificationsLink() {
		return this.notificationsLink;
	}

	public MaterialLink getNavigationLink() {
		return this.navigationLink;
	}

	public MaterialLink getFavoriteLink() {
		return this.favoriteLink;
	}
	
	public interface Presenter {
		
		
		void logoutClickEvent();
	}

}
