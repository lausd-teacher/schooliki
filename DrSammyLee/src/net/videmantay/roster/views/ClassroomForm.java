package net.videmantay.roster.views;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;
import java.util.Date;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialChip;
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
	public MaterialBadge joinRequestBadge;
	
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
					MaterialChip chip = new MaterialChip();
					chip.setFontSize("1em");
					chip.setBackgroundColor("grey darken-3");
					chip.setTextColor("white");
					chip.setLetterBackgroundColor("white");
					chip.setLetterColor("grey darken-2");
					chip.setMarginTop(5);
					chip.setIconType(IconType.CLOSE);
					chip.setText(value.getEmail());
					chip.setDataAttribute("email", value.getEmail());
					if(value.getImageUrl() != null && !value.getImageUrl().isEmpty()){
						chip.setUrl(value.getImageUrl());
					}else{
						chip.setLetter(value.getEmail().substring(0, 1).toUpperCase());
					}
						JoinRequestJson jr = JoinRequestJson.createObject().cast();
						jr.setEmail(value.getEmail());
						jr.setStatus(JoinStatus.ACCEPTED);
						$(chip).attr("data-student", jr);
			
						
					joinRequestCreatedPanel.add(chip);
					$(parent).hide();
			}
	};

	@UiField(provided=true)
	CellList<AppUserJson> joinRequestResultsPanel = new CellList<AppUserJson>(appUserCell, dataProvider);
	
	
	@UiField
	MaterialTextBox joinRequestSearchInput;
	
	@UiField
	MaterialPanel joinRequestCreatedPanel;

	@UiField
	AbsolutePanel jrResultPanelWrapper;
	
	public  Long lastClick = new Long(0L);
	
	final RosterUtils utils;
	

	public ClassroomForm(RosterUtils ru) {
		this.utils = ru;
		initWidget(uiBinder.createAndBindUi(this));
		
		//constructor not give the utils///
		//look a populate jr requests////
		
		
		//add click handler to buttons/////////
		joinRequestSearchInput.addKeyPressHandler(new KeyPressHandler(){

			@Override
			public void onKeyPress(KeyPressEvent event) {
				Long newClick = new Date().getTime();
				Timer timer = new Timer(){

					@Override
					public void run() {
						String query = joinRequestSearchInput.getValue();
						//if string is empty
					
						console.log("key pressed value: " + query);
						if(query == null || query.isEmpty() || query.trim().isEmpty()){
							console.log("string is empty");
							$(jrResultPanelWrapper).css("overflow", "hidden");
							
						}else{
							Ajax.get(RosterUrl.studentSearch(), $$("q:" + query)).done(new Function(){
								@Override
								public void f(){
									JsArray<AppUserJson> data = JsonUtils.safeEval((String)this.arguments(0));
									ArrayList<AppUserJson> values = new ArrayList<AppUserJson>();
									for(int i = 0; i< data.length(); i++){
										values.add(data.get(i));
									}
									if($(jrResultPanelWrapper).css("overflow").equalsIgnoreCase("hidden")){
										$(jrResultPanelWrapper).css("overflow", "visible");
									}
									console.log("Values form student search is ");
									console.log(values);
									joinRequestResultsPanel.setRowData(values);
									joinRequestResultsPanel.setRowCount(10);
								}
							});
						}
						
				
					}};
				if(lastClick != 0 &&newClick < (lastClick + 2000)){
					timer.cancel();
					timer.schedule(2000);
					lastClick = new Date().getTime();
				}else{
				timer.schedule(500);
				lastClick = new Date().getTime();
				}
			}});
		
		
	}
	
	public Promise submit(){
		//see how many children on joinRequestCreatedPanel
		GQuery $createdJr = $(joinRequestCreatedPanel).children();
		console.log("child num for joinrequest create panel " + $createdJr.length());
		if($createdJr.length() > 0){
			$($createdJr).each(new Function(){
				@Override
				public void f(){
					JoinRequestJson jreq = JoinRequestJson.createObject().cast();
					jreq.setEmail((String)$(this).attr("data-email"));
					jreq.setStatus(JoinStatus.ACCEPTED);
					joinRequestList.add(jreq);
				}
			});
		}
		console.log("form submit inner workings");
		if(joinRequestList.size() <1){
			return null;
		}
		JsArray<JoinRequestJson> sendMe = JsArray.createArray().cast();
		for(JoinRequestJson j: joinRequestList){
			sendMe.push(j);
		}
		console.log(sendMe);
		return Ajax.ajax(Ajax.createSettings()
				.setContentType("application/json")
				.setDataType("json")
				.setData(sendMe)
				.setUrl(RosterUrl.student(utils.getCurrentRoster().getId()))
				.setType("POST")
				);
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
	
	public void populateJoinRequests(final JsArray<JoinRequestJson> requests){
		//first init the badge
		if(requests.length() < 1){
			joinRequestBadge.setVisible(false);
			return;
		}else{
			joinRequestBadge.setText(requests.length()+"");
		}
		
		//iterate and add new jrItems///
		for(int i = 0; i < requests.length(); i++){
			final JoinRequestItem jrItem = new JoinRequestItem();
			jrItem.setData(requests.get(i));
			
			jrItem.acceptRequestBtn.addClickHandler(new ClickHandler(){
			
				@Override
				public void onClick(ClickEvent event) {
					final JoinRequestJson jrj = JoinRequestJson.createObject().cast();
					jrj.setEmail(jrItem.itemLabel.getText());
					$(jrItem).animate($$("background-color:green"), 500, new Function(){
						@Override
						public void f(){
							$(this).hide();
							accept(jrj);
						}
					});
					
				}});
			jrItem.dismissRequestBtn.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					final JoinRequestJson jrj = JoinRequestJson.createObject().cast();
					jrj.setEmail(jrItem.itemLabel.getText());
					$(jrItem).animate($$("background-color:red"), 500, new Function(){
						@Override
						public void f(){
							$(this).hide();
							dismiss(jrj);
						}
					});
				}});
			
			joinRequestCollection.add(jrItem);
			joinRequestCollection.setVisible(true);
			emptyJoinRequest.setVisible(false);
		}
	}
	
	
}
