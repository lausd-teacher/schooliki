package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialNavSection;
import gwt.material.design.client.ui.MaterialSideNav;

public class AppLayout extends Composite {

	private static AppLayoutUiBinder uiBinder = GWT.create(AppLayoutUiBinder.class);

	interface AppLayoutUiBinder extends UiBinder<Widget, AppLayout> {
	}
	
	@UiField
	MaterialNavBar navBar;
	
	@UiField
	MaterialNavBrand navBartitle;
	
	@UiField
	MaterialNavSection navBarContainer;
	
	@UiField
	MaterialSideNav sideNav;
	
	@UiField
	MaterialContainer mainPanel;

	public MaterialNavBar getNavBar() {
		return this.navBar;
	}

	public MaterialSideNav getSideNav() {
		return this.sideNav;
	}

	public MaterialContainer getMainPanel() {
		return this.mainPanel;
	}

	public void setNavBar(MaterialNavBar navBar) {
		this.navBar = navBar;
	}

	public void setSideNav(MaterialSideNav sideNav) {
		this.sideNav = sideNav;
	}

	public void setMainPanel(MaterialContainer mainPanel) {
		this.mainPanel = mainPanel;
	}

	public MaterialNavBrand getNavBartitle() {
		return this.navBartitle;
	}

	public MaterialNavSection getNavBarContainer() {
		return this.navBarContainer;
	}

	public AppLayout() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
