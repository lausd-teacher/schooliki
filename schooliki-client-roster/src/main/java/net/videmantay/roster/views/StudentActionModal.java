package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialModal;

public class StudentActionModal extends Composite {

	private static StudentActionModalUiBinder uiBinder = GWT.create(StudentActionModalUiBinder.class);

	interface StudentActionModalUiBinder extends UiBinder<Widget, StudentActionModal> {
	}

	
	//html for more button on incident grid
	final String moreHtml = "<div class='moreItems grey lighten-3 center-align' style='cursor:pointer;height:100px; border:2px solid Silver; margin-top:2em' >" +
			"<span style='display:block; font-size:2em;margin-left:auto; margin-right:auto'>More</span>"+
			"<i class='material-icons' style='font-size:3em;margin-left:auto; margin-right:auto;height:3em;color:Silver'>add_circle</i></div>";
	
	

	
	
	@UiField
	MaterialModal modal;
	


	
	public StudentActionModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	
	public void show(){
		modal.openModal();
	}
	public void hide(){
		modal.closeModal();
	}

	@Override
	public void onLoad(){
		
		
	}

}
