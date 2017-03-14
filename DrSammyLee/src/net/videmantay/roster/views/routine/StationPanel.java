package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import net.videmantay.roster.routine.json.StationJson;

public class StationPanel extends Composite {
	
	
	private  StationJson station;
	private StationJson stationCopy;
	
	private static StationPanelUiBinder uiBinder = GWT.create(StationPanelUiBinder.class);

	interface StationPanelUiBinder extends UiBinder<Widget, StationPanel> {
	}

	public StationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void edit(){
		stationCopy = station.copy();
	}
	
	public void save(){
		
	}
	
	public void cancel(){
		
	}
	
	public void setData(StationJson station){
		this.station = station;
	}
	
}
