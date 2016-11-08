package net.videmantay.roster.views.draganddrop;

import java.util.ArrayList;
import java.util.List;

import net.videmantay.roster.views.components.FurniturePanelItem;

public class DragAndDropManager {
	
	
	
	
	static final String SINGLE_DESK = "single desk";
	static final String DOUBLE_DESK = "double desk";
	
	static final String SINGLE_DESK_IMAGE_URL = "img/singleDesk.png";
	static final String DOUBLE_DESK_IMAGE_URL = "img/doubleDesk.png";
	
	
	
	public static List<FurniturePanelItem> getFurnitureItems(){
		List<FurniturePanelItem> furnitureList = new ArrayList<FurniturePanelItem>();
		furnitureList.add(new FurniturePanelItem(SINGLE_DESK_IMAGE_URL, SINGLE_DESK));
		furnitureList.add(new FurniturePanelItem(DOUBLE_DESK_IMAGE_URL, DOUBLE_DESK));
		
		return furnitureList;
	}

}
