package net.videmantay.roster.student;

import com.floreysoft.gwt.picker.client.callback.AbstractPickerCallback;
import com.floreysoft.gwt.picker.client.domain.Feature;
import com.floreysoft.gwt.picker.client.domain.Picker;
import com.floreysoft.gwt.picker.client.domain.PickerBuilder;
import com.floreysoft.gwt.picker.client.domain.ViewId;
import com.floreysoft.gwt.picker.client.domain.result.BaseResult;
import com.floreysoft.gwt.picker.client.domain.result.PhotoResult;
import com.floreysoft.gwt.picker.client.domain.result.ViewToken;
import com.floreysoft.gwt.picker.client.utils.PickerLoader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.roster.RosterEvent;
import net.videmantay.shared.url.RosterUrl;
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
	FormPanel form;
	
	@UiField
	MaterialTextBox schoolEmail;
	
	@UiField
	MaterialTextBox firstName;
	
	@UiField
	MaterialTextBox lastName;
	
	@UiField
	MaterialTextBox extName;
	
	@UiField
	MaterialDatePicker DOB;
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialModalContent modalContent;
	
	private  RosterStudentJson student = RosterStudentJson.createObject().cast();
	
	private final RosterJson roster  = window.getPropertyJSO("roster").cast();
	
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
      
      private ClickHandler handler = new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			modal.closeModal();
			picker.setVisible(true);
		}};
	
	private ClickHandler okHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			event.stopPropagation();
			getFormData();
			form.reset();
			modal.closeModal();
			MaterialLoader.showLoading(true);
			Ajax.post(RosterUrl.CREATE_STUDENT,$$("student:" + JsonUtils.stringify(student)))
			.done(new Function(){
				@Override
				public void f(){
					console.log("create student form done return : " +  this.getArgument(0));
					RosterStudentJson rosStu = JsonUtils.safeEval((String)this.getArgument(0));
					RosterJson  roster = window.getPropertyJSO("roster").cast();
					roster.getRosterStudents().push(rosStu);
					$(body).trigger(RosterEvent.STUDENT_LIST_UPDATED);
					MaterialLoader.showLoading(false);
				}
			});
			
		}};
	
	private ClickHandler cancelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			student = RosterStudentJson.createObject().cast();
			form.reset();
			modal.closeModal();
			
		}};
	
	
	private Picker picker;
	private final LoginInfo info = LoginInfo.create();
	
	
///Constructor Here , Almost lost it//////////////////////////////////////////////
	public CreateStudentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		console.log("info to string authToken" + info.getAuthToken());
		//give form a size
		//set up the picker
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
		form.reset();
		student = null;
	}
	
	private RosterStudentJson getFormData(){
		
		student.setAcctId(schoolEmail.getValue());
		student.setFirstName(firstName.getValue());
		student.setLastName(lastName.getValue());
		student.setExtName(extName.getValue());
		student.setDOB(df.format(DOB.getDate()));
		student.setRoster(roster.getId());
		return student;
	}
	
	
	
	@Override
	public void onLoad(){
						
				//picker button clickHandler
				pickerButton.addClickHandler(handler);
				okBtn.addClickHandler(okHandler);
				cancelBtn.addClickHandler(cancelHandler);
				student = RosterStudentJson.createObject().cast();
				
	}
	

}
