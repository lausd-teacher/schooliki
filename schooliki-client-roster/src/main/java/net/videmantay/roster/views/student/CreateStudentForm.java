package net.videmantay.roster.views.student;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.masonry.MaterialMasonry;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.json.AppUserJson;


public class CreateStudentForm extends Composite{

	private static CreateStudentFormUiBinder uiBinder = GWT.create(CreateStudentFormUiBinder.class);

	interface CreateStudentFormUiBinder extends UiBinder<Widget, CreateStudentForm> {
	}
	
	@UiField
	MaterialButton okBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	@UiField
	MaterialMasonry availableStudentsMasonery;
	
	@UiField
	MaterialMasonry addedStudentsMasonery;
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialModalContent modalContent;
	
	@UiField
	MaterialRow availableStudentContainer;

	JsArray<AppUserJson> studentList = JavaScriptObject.createArray().cast();
	
	HTMLPanel test;

	public CreateStudentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		modalContent.setDisplay(Display.BLOCK);
		Ajax.get("/appuser").done(new Function() {
			@Override
			public void f() {
				GWT.log(this.arguments(0).toString());
				studentList = JsonUtils.safeEval((String)this.arguments(0)).cast();
				renderStudentList();
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
				Window.alert("Error getting the list of students, Please try again later");
			}
		});
	}
	///////////////////END CONSTR////////////////////////////////////////////////
	
	public void show(){
		modal.openModal();
		
	}
	
	public void hide(){
		modal.closeModal();
	}
	
	public MaterialButton getCancelBtn() {
		return this.cancelBtn;
	}


	public MaterialButton getOkBtn() {
		return this.okBtn;
	}

	
	
	
	public MaterialModal getModal() {
		return this.modal;
	}

private void renderStudentList(){
	
	for(int i = 0; i < studentList.length(); i++){
		AppUserJson current = studentList.get(i);
		final StudentCard card = new StudentCard(current.getImageUrl(), current.getName(), current.geteMail(), current.getId());
		
//		MaterialColumn column = new MaterialColumn();
//		column.add(card);
		availableStudentContainer.add(card);
		
		
		//Very weird behavior, when there is these two handlers the click works, when there is only one of them click does not work
		
		
		$(card).dblclick(new Function(){
			@Override
			public void f() {
				
			}
		});
		
		
		
		 card.addHandler(new DoubleClickHandler(){
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				
				addedStudentsMasonery.add(card);
				availableStudentContainer.remove(card);
				 card.getRemoveButton().setDisplay(Display.BLOCK);
				 card.getRemoveButton().addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						addedStudentsMasonery.remove(card);
						availableStudentContainer.add(card);
						card.getRemoveButton().setDisplay(Display.NONE);
					}
				 });
			}
		 }, DoubleClickEvent.getType());
		}
	}

  public MaterialMasonry getAddedStudentsMasonery() {
	return this.addedStudentsMasonery;
  }

public interface Presenter{
	  
	  void okButtonClickHandler();
	  void cancelButtonClickHandler();

  }
	

}
