package net.videmantay.roster.views.classtime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.ui.Ui.Ui;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.shared.url.RosterUrl;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.RosterJson;

public class ClassTimeForm extends Composite {

	private static ClassTimeFormUiBinder uiBinder = GWT.create(ClassTimeFormUiBinder.class);

	interface ClassTimeFormUiBinder extends UiBinder<Widget, ClassTimeForm> {
	}
	
	@UiField
	MaterialButton submitBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	@UiField
	FormPanel classtimeForm;
	
	private Function saveClassTimeFunc = new Function(){
											@Override
											public void f(){
												this.arguments(0);
											}
															};
	
	private final RosterJson roster =window.getPropertyJSO("roster").cast();
	private ClassTimeJson classTime;
	private Boolean newClasstime;
	private final RunAsyncCallback procedureEditorCallback = new RunAsyncCallback(){

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			
		}};
		
	private final RunAsyncCallback groupEditorCallback =new RunAsyncCallback(){

		@Override
		public void onFailure(Throwable reason) {
			
			
		}

		@Override
		public void onSuccess() {
			
			
		}};

	public ClassTimeForm() {
		console.log("ClassTime Form constructor");
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	public void onLoad(){
		
		$(".tabs").as(Ui).click(new Function(){
			@Override
			public boolean f(Event e){
				switch($(this).find("a").attr("href")){
				case "#procedureEditor":GWT.runAsync(procedureEditorCallback);break;
				case"#groupEditor":GWT.runAsync(groupEditorCallback);break;
				default:return true;
				}
				
				return true;
			}
		});
	}
	
	@Override
	public void onUnload(){
		this.classTime = null;
		this.newClasstime = null;
		this.classtimeForm.reset();	
	}
	
	public void setClassTime(ClassTimeJson classtime){
		if(classtime == null){
			this.classTime = ClassTimeJson.createObject().cast();
			newClasstime= true;
		}else{
			this.classTime = classtime;
			newClasstime = false;
		}
	}//end set classTime
	
	private void submit(){
		//push values to classTime
		
		
		if(newClasstime){
			roster.getClassTimes().push(classTime);
			Ajax.post(RosterUrl.CREATE_CLASSTIME, $$("roster:" + roster.getId()+", classtime:" + classTime))
			.done(saveClassTimeFunc);
		}
		//do ajax save on roster
		//set current classtime to the one we just save(on update that should be true) that was saved
		Ajax.post("/teacher/updateroster" , $$("roster:" + roster.getId() + ",classtime:" + classTime))
		.done(saveClassTimeFunc);
	}//end submit
	
	private void cancel(){
		
	}
}
