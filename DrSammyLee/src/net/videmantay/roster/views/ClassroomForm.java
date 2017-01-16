package net.videmantay.roster.views;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;
import java.util.Date;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.JoinRequestJson;
import net.videmantay.roster.json.JoinStatus;
import net.videmantay.student.json.AppUserJson;


public class ClassroomForm extends Composite {

	private static ClassroomFormUiBinder uiBinder = GWT.create(ClassroomFormUiBinder.class);

	interface ClassroomFormUiBinder extends UiBinder<Widget, ClassroomForm> {
	}
	
	
	final ArrayList<JoinRequestJson>joinRequestList = new ArrayList<JoinRequestJson>();
	
		
	@UiField
	public MaterialAnchorButton submitBtn;
	
	@UiField
	public MaterialAnchorButton cancelBtn;
	
	@UiField
	public MaterialCollection joinRequestCollection;
	final int SEARCH_LENGTH = 50;
	String pageNum ="0";
	
	@UiField
	public HTMLPanel emptyJoinRequest;
	//need for the cell list

	 AsyncDataProvider<AppUserJson> dataProvider = new AsyncDataProvider<AppUserJson>(){

		@Override
		protected void onRangeChanged(HasData<AppUserJson> display) {
		Range range =	display.getVisibleRange();
		int rangeLength = range.getLength();
		int startIndex = range.getStart();
		if(rangeLength - startIndex <= 10){
			//Ajax for more
			
		}
		}
		
		@Override
		public Long getKey(AppUserJson item){
			return item.getId();
		}
	};
	////////cell for list
	 AbstractCell<AppUserJson> appUserCell = new AbstractCell<AppUserJson>("click"){

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context, AppUserJson value, SafeHtmlBuilder sb) {
			MaterialRow row = new MaterialRow();
			MaterialColumn imgCol =new MaterialColumn();
			MaterialImage img = new MaterialImage();
			img.setCircle(true);
			imgCol.add(img);
			img.setUrl(value.getImageUrl());
			MaterialColumn emailCol = new MaterialColumn();
			MaterialLabel emailLabel = new MaterialLabel();
			emailLabel.setText(value.getEmail());
			emailCol.add(emailLabel);
			row.add(imgCol);
			row.add(emailCol);
			
			sb.appendHtmlConstant(row.toString());
			
		}
			@Override
			public void onBrowserEvent(Cell.Context context,
                    Element parent,
                    AppUserJson value,
                    NativeEvent event,
                    ValueUpdater<AppUserJson> valueUpdater){
					//get flowPanel and a joinRequestChip
			}
	};

	@UiField(provided=true)
	CellList<AppUserJson> joinRequestResultsPanel = new CellList<AppUserJson>(appUserCell, dataProvider);
	
	@UiField
	MaterialTextBox joinRequestSearchInput;
	
	@UiField
	MaterialPanel joinRequestCreatedPanel;

	public  Long lastClick = new Long(0L);
	public ClassroomForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//better by value and using the roter value
		joinRequestSearchInput.addKeyPressHandler(new KeyPressHandler(){

			@Override
			public void onKeyPress(KeyPressEvent event) {
				Long newClick = new Date().getTime();
				Timer timer = new Timer(){

					@Override
					public void run() {
						String query = joinRequestSearchInput.getValue();
						//if string is empty
						if(query == null || query.isEmpty()){
							joinRequestResultsPanel.getVisibleItems().clear();
							$(joinRequestResultsPanel).hide();
							return;
						}
						Ajax.get(RosterUrl.studentSearch(), $$("q:" + query)).done(new Function(){
							@Override
							public void f(){
								JsArray<AppUserJson> data = JsonUtils.safeEval((String)this.arguments(0));
								ArrayList<AppUserJson> values = new ArrayList<AppUserJson>();
								for(int i = 0; i< data.length(); i++){
									values.add(data.get(i));
								}
								if(!joinRequestResultsPanel.isVisible()){
									joinRequestResultsPanel.setVisible(true);
								}
								console.log("Values form student search is ");
								console.log(values);
								joinRequestResultsPanel.setRowData(values);
							}
						});
				
					}};
				if(lastClick != 0 &&newClick < (lastClick + 2000)){
					timer.cancel();
					timer.schedule(2000);
					lastClick = new Date().getTime();
				}else{
				timer.schedule(1000);
				lastClick = new Date().getTime();
				}
			}});
		
	}
	
	public Promise submit(RosterUtils utils){
		JsArray<JoinRequestJson> sendMe = JsArray.createArray().cast();
		for(JoinRequestJson j: joinRequestList){
			sendMe.push(j);
		}
		return Ajax.post(RosterUrl.student(utils.getCurrentRoster().getId()), $$(sendMe.toSource()));
	}
	public void cancel(){
		
	}
	
	public void accept(JoinRequestJson jr){
		jr.setStatus(JoinStatus.ACCEPTED);
		joinRequestList.add(jr);
	}
	
	public void dismiss(JoinRequestJson jr){
		jr.setStatus(JoinStatus.DISMISSED);
		joinRequestList.add(jr);
	}
	
	public void pending(JoinRequestJson jr){
		joinRequestList.remove(jr);
		jr.setStatus(JoinStatus.PENDING);
	}
	
	public void showEmptyJoinRequest(){
		
	}
	
	
}
