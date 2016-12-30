package net.videmantay.admin.views;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.ui.MaterialPreLoader;
import gwt.material.design.client.ui.MaterialSpinner;
import net.videmantay.admin.ClientFactory;
import net.videmantay.admin.json.AppUserJson;

public class AppUserDataTable extends DataGrid<AppUserJson> {

	private static final AppUserAsyncDataProvider dataProv = new AppUserAsyncDataProvider();
	
	
	private final AppUserDeleteModal deleteModal;
	
	private final ClientFactory factory;
	
	TextColumn<AppUserJson> emailCol = new TextColumn<AppUserJson>(){

		@Override
		public String getValue(AppUserJson object) {
			return object.getEmail();
			
		}};
		
	TextColumn<AppUserJson> firstNameCol = new TextColumn<AppUserJson>(){

			@Override
			public String getValue(AppUserJson object) {
				return object.getFirstName();
			}};
	TextColumn<AppUserJson> lastNameCol = new TextColumn<AppUserJson>(){

				@Override
				public String getValue(AppUserJson object) {
					return object.getLastName();
				}};	

	TextColumn<AppUserJson> rolesCol = new TextColumn<AppUserJson>(){
		@Override
		public String getValue(AppUserJson object){
			StringBuilder sb = new StringBuilder();
			ArrayList<String> list = new ArrayList<String>();
			for(String s: object.getRoles()){
				list.add(s);
			}
			Collections.sort(list);
			
			for(String s: list){
				sb.append(s +"\n");
			}//end for
			return sb.toString().trim();
		}};
	TextColumn<AppUserJson> statusCol = new TextColumn<AppUserJson>(){

		@Override
		public String getValue(AppUserJson object) {
			   if(object.isActive())
				    return "active";
			   
			return "inactive";
		}};
	AbstractCell<AppUserJson> actionCell = new AbstractCell<AppUserJson>("click"){
		@Override
		public void onBrowserEvent(Cell.Context context, Element parent, AppUserJson value, NativeEvent event, ValueUpdater<AppUserJson> valueUpdater){
			if($(event.getEventTarget()).hasClass("delete-user")){
				//fire a show deleteModal
				GWT.log("delete button hit" + value.getEmail());
				factory.setCurrentSelectedUser(value);
				deleteModal.show(value);
			}
			if($(event.getEventTarget()).hasClass("update-user")){
				
				GWT.log("update button hit" + value.isActive());
				deleteModal.show(value);
			}
			
		}
		
		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context, AppUserJson value, SafeHtmlBuilder sb) {
			String html = "";
			if(value.isActive())
			   html = "<div><span class='action-icon'><i class='delete-user material-icons'/>visibility_off</span><span class='action-icon'><i class='update-user material-icons'/>edit</span></div>";
			else
			   html = "<div><span class='action-icon'><i class='delete-user material-icons'/>visibility</span><span class='action-icon'><i class='update-user material-icons'/>edit</span></div>";
			 
			sb.appendHtmlConstant(html);
		}};
	IdentityColumn<AppUserJson> actionCol = new IdentityColumn<AppUserJson>(actionCell){};
	//loader
	MaterialSpinner spinner = new MaterialSpinner();
	MaterialPreLoader pre = new MaterialPreLoader();
	//empty table
	HTMLPanel emptyTable = new HTMLPanel("<div class='errorTable'><h2>No Data</h2><p>It appears that you have no registered user."
										+"<br/>No problem just hit the big plus button at the bottom of the screen"
										+"</p></div>");
	
	
	public AppUserDataTable(AppUserDeleteModal deleteModal, ClientFactory factory){
		
		super(dataProv);
		this.deleteModal = deleteModal; 
		this.factory = factory;
		dataProv.addDataDisplay(this);
		this.addStyleName("striped responsive-table");
		
		
		this.addColumn(emailCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>E-mail</h6>"));
		this.addColumn(firstNameCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>First Name</h6>"));
		this.addColumn(lastNameCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>Last Name</h6>"));
		this.addColumn(rolesCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>Roles</h6>"));
		this.addColumn(statusCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>Status</h6>"));
		this.addColumn(actionCol);	
		pre.add(spinner);
		this.setLoadingIndicator(pre);
		this.setEmptyTableWidget(emptyTable);
		
		this.setMinimumTableWidth(350, Unit.PX);
		this.setHeight(body.getClientHeight() +"px");
		
		
	}
}
