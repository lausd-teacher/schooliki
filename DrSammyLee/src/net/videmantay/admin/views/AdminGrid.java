package net.videmantay.admin.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.admin.AdminUrl;
import net.videmantay.admin.json.AppUserJson;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.*;
import com.google.gwt.query.client.plugins.ajax.Ajax;

public class AdminGrid extends Composite {

	private static AdminMainUiBinder uiBinder = GWT.create(AdminMainUiBinder.class);

	interface AdminMainUiBinder extends UiBinder<Widget, AdminGrid> {
	}
	
	
	private final AppUserDataTable grid;

	private final AppUserForm form;
	private final AppUserDeleteModal deleteModal;
	

	@UiField
	MaterialButton fab;
	
	@UiField
	HTMLPanel container;
	

	public AdminGrid(AppUserDataTable grid, AppUserForm form, AppUserDeleteModal deleteModal) {
		initWidget(uiBinder.createAndBindUi(this));
		this.grid = grid;
		this.form = form;
		this.deleteModal = deleteModal;
		container.add(this.grid);
		container.add(this.form);
		container.add(this.deleteModal);
		this.grid.redraw();
	}
	
	
	
	public MaterialButton getFab() {
		return this.fab;
	}

	public AppUserForm getForm() {
		return this.form;
	}
	
	public AppUserDataTable getGrid() {
		return this.grid;
	}


	public interface Presenter{
		
		void fabButtonClickEvent();
		
	}
	

	
	

}
