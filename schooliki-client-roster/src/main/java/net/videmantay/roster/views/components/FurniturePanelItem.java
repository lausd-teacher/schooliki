package net.videmantay.roster.views.components;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLabel;
import gwtquery.plugins.ui.DraggableUi;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;

public class FurniturePanelItem extends Composite {

	private static FurniturePanelItemUiBinder uiBinder = GWT.create(FurniturePanelItemUiBinder.class);

	interface FurniturePanelItemUiBinder extends UiBinder<Widget, FurniturePanelItem> {
	}
	

	
	@UiField
	ImageElement furnitureImage;
	
	@UiField
	MaterialLabel furnitureName;

	public FurniturePanelItem(String imgUrl, String funitureName) {
		initWidget(uiBinder.createAndBindUi(this));
		furnitureImage.setSrc(imgUrl);
		furnitureImage.getStyle().setHeight(50, Unit.PX);
		furnitureImage.getStyle().setWidth(50, Unit.PX);
		furnitureImage.addClassName("furnitureItem");
		furnitureName.setText(funitureName);
		setUpDragAndDrop();
	}
	
	//furniture item is both dragable and droppable
	private void setUpDragAndDrop(){
		Draggable.Options options = Draggable.Options.create();
		options.revert("invalid");
		options.helper("clone");
		options.grid(20, 20);
		options.containment("window");
		
		Droppable.Options options2 = Droppable.Options.create();
		options2.accept(".studentDraggable");
		
		
		$(furnitureImage).as(Ui).draggable(options);
		
		$(furnitureImage).as(Ui).droppable(options2);
		
		
	}

}
