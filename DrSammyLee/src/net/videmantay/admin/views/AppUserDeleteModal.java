package net.videmantay.admin.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.admin.AdminUrl;
import net.videmantay.admin.json.AppUserJson;

public class AppUserDeleteModal extends Composite {

	private static AppUserDeleteModalUiBinder uiBinder = GWT.create(AppUserDeleteModalUiBinder.class);

	interface AppUserDeleteModalUiBinder extends UiBinder<Widget, AppUserDeleteModal> {
	}

	@UiField
	MaterialModal deleteModal;
	
	@UiField
	MaterialAnchorButton confirmDeleteBtn;
	
	@UiField
	MaterialAnchorButton cancelDeleteBtn;
	
	@UiField
	HeadingElement deletedUserHeading;
	
	
	public AppUserDeleteModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	
	public void show(AppUserJson user){
		$(deletedUserHeading).html("<span>" + user.getEmail() + "<br/>" 
				+ user.getFirstName() +"&nbsp;" + user.getLastName() 
				+"</span>");
		deleteModal.openModal();
	}
	
	public void close(){
		deleteModal.closeModal();
	}

	public MaterialAnchorButton getConfirmDeleteBtn() {
		return this.confirmDeleteBtn;
	}

	public MaterialAnchorButton getCancelDeleteBtn() {
		return this.cancelDeleteBtn;
	}
	
	public interface Presenter{
		void confirmDeleteClickEvent();
		void cancelDeleteClickEvent();
	}
	
}
