package net.videmantay.roster.views.routine;

import com.google.api.client.util.Data;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import net.videmantay.roster.routine.json.SlotJson;
import net.videmantay.roster.routine.json.StationJson;
import net.videmantay.roster.routine.json.StationManagerJson;
import net.videmantay.roster.routine.json.TimeSlotJson;

public class TimeSlotChart extends Composite {

	private static TimeSlotChartUiBinder uiBinder = GWT.create(TimeSlotChartUiBinder.class);

	interface TimeSlotChartUiBinder extends UiBinder<Widget, TimeSlotChart> {
	}

	public TimeSlotChart() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	public MaterialCollection stationCollection;
	
	@UiField
	public FlexTable slotsTable;
	
	@UiField
	HTMLPanel timeSlotChart;
	
	public StationManagerJson smj;
	
	public void setData(StationManagerJson data){
		//populate the stations collection
		stationCollection.clear();
		for(int i = 0; i < data.getStations().length(); i++){
			StationJson staData = data.getStations().get(i);
			MaterialButton station = new MaterialButton();
			station.setDataAttribute("stationNum", staData.getNumber() +"");
			station.setBackgroundColor(staData.getColor());
			station.setText(staData.getTitle());
			station.setTextColor("white");
			MaterialCollectionItem item = new MaterialCollectionItem();
			item.add(station);	
		}//end for
		
		//so now we need to match time slots to stations
		slotsTable.clear();
		int colNum = 1;
		for(int i = 1; i <= data.getTimeSlots().length(); i++){
			TimeSlotJson tsj = data.getTimeSlots().get(i - 1);
			
			slotsTable.insertCell(i, colNum);
			slotsTable.setHTML(1, colNum, "<span class='time-slot-header'>" + tsj.getStartTime() + "</span");
			for(int j = 1; j <= tsj.getSlots().length(); j++){
				SlotJson sj = tsj.getSlots().get(j - 1);
			slotsTable.insertCell(1 + j, colNum);
			String cellInfo = sj.GroupId() ==null? "sp" : sj.GroupId().toString();
			slotsTable.setHTML(1 + j, colNum, "<span class='slot-group-cell'>" + cellInfo + "</span>");
			}
			
		}
		
		
		
	}

}
