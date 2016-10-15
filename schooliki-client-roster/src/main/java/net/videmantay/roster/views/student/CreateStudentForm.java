package net.videmantay.roster.views.student;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;

import com.floreysoft.gwt.picker.client.callback.AbstractPickerCallback;
import com.floreysoft.gwt.picker.client.domain.Picker;
import com.floreysoft.gwt.picker.client.domain.PickerBuilder;
import com.floreysoft.gwt.picker.client.domain.ViewId;
import com.floreysoft.gwt.picker.client.domain.result.BaseResult;
import com.floreysoft.gwt.picker.client.domain.result.PhotoResult;
import com.floreysoft.gwt.picker.client.domain.result.ViewToken;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.LoginInfo;
import net.videmantay.student.json.RosterStudentJson;


public class CreateStudentForm extends Composite{

	private static CreateStudentFormUiBinder uiBinder = GWT.create(CreateStudentFormUiBinder.class);

	interface CreateStudentFormUiBinder extends UiBinder<Widget, CreateStudentForm> {
	}
	
	
	@UiField
	MaterialImage studentImg;
	
	@UiField
	MaterialLabel imgUrl;
	
	@UiField
	MaterialButton pickerButton;
	
	@UiField
	MaterialButton okBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	@UiField
	MaterialInput schoolEmail;
	
	@UiField
	MaterialInput firstName;
	
	@UiField
	MaterialInput lastName;
	
	@UiField
	MaterialInput extName;
	
	@UiField
	MaterialDatePicker DOB;
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialModalContent modalContent;
	
	@UiField
	HTMLPanel formContainer;
	
	private  RosterStudentJson student = RosterStudentJson.createObject().cast();
	
	private final RosterJson roster  = RosterStudentJson.createObject().cast();
	
	private DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
	
	private AbstractPickerCallback callback = new AbstractPickerCallback() {
        
        @Override
        public void onCanceled() {
          picker.setVisible(false);
          modal.openModal();
        }

		@Override
		public void onPicked(ViewToken viewToken, BaseResult result) {
			modal.openModal();
			PhotoResult pr = result.cast();
			student.setThumbnails(pr.getDocs().get(0).getThumbnails());
			String url = pr.getDocs().get(0).getThumbnails().get(pr.getDocs().get(0).getThumbnails().length() -1).getUrl();
			studentImg.setUrl(url);
			imgUrl.setText(url);
			picker.setVisible(false);
			
		}
      };
      

	
	
	HTMLPanel test;
	private Picker picker;


	private final LoginInfo info = LoginInfo.create();
	
	
	public CreateStudentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		formContainer.getElement().setId("createStudentForm");
		console.log("info to string authToken" + info.getAuthToken());

		modalContent.setDisplay(Display.BLOCK);
				
						picker = PickerBuilder.create()
								.addView(ViewId.PHOTOS)
								.addView(ViewId.PHOTO_UPLOAD)
								.setDeveloperKey("AIzaSyCpTydZp0Hs-TmmM5pn7t_xgCSg1_SBy2o")
								.setOAuthToken(info.getAuthToken())
								.setSize(900, 600)
								.addCallback(callback)
								.build();
						picker.setVisible(false);
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

	public MaterialButton getPickerButton() {
		return this.pickerButton;
	}

	public Picker getPicker() {
		return this.picker;
	}
	
	public RosterStudentJson getFormData(){
		
		RosterStudentJson newStudent = JavaScriptObject.createObject().cast();
		
		newStudent.setAcctId(schoolEmail.getValue());
		newStudent.setFirstName(firstName.getValue());
		newStudent.setLastName(lastName.getValue());
		newStudent.setExtName(extName.getValue());
		newStudent.setDOB(df.format(DOB.getDate()));
		newStudent.setRoster(roster.getId());
		return newStudent;
	}
	
	
	
	public MaterialModal getModal() {
		return this.modal;
	}

	@Override
	public void onLoad(){
			
		$("#createStudentForm input").blur(getValidationFunction());				
				$(".errorLabel").hide();
				$("#errorDateOfBirthLabel").hide();
				
				
		DOB.addCloseHandler(new CloseHandler<MaterialDatePicker>(){
			@Override
			public void onClose(CloseEvent<MaterialDatePicker> event) {
			      if(DOB.getDate() == null){
			    	  $("#errorDateOfBirthLabel").show();
			      }else{
			    	  $("#errorDateOfBirthLabel").hide();
			      }
			      
			      
			}
		});
			
	}
	
	
private  Function getValidationFunction(){
		
		return new Function(){
			@Override
			public void f(){
				GWT.log("event" + $(this).id());
				if($(this).is(":invalid")){
					$(this).next(".errorLabel").show();
					$(this).addClass("inputError");
				} else{
					$(this).next(".errorLabel").hide();
				}
			}
		};
		
	}

  public interface Presenter{
	  
	  void okButtonClickHandler();
	  void cancelButtonClickHandler();
	  void pickerButtonClick();
  }
	

}
