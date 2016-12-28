package net.videmantay.roster.views.classtime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.RosterJson;

public class ClassTimeGrid extends Composite {

	@UiField
	MaterialColumn cardCol;
	
	@UiField
	MaterialAnchorButton createBtn;
	
	@UiField
	HTMLPanel container;
	
	private final RosterJson roster;
	
	private final ClassTimeJson currrentClassTime;
	
	private static ClassTimeMainUiBinder uiBinder = GWT.create(ClassTimeMainUiBinder.class);

	interface ClassTimeMainUiBinder extends UiBinder<Widget, ClassTimeGrid> {
	}

	
	public ClassTimeGrid(ClientFactory factory) {
		initWidget(uiBinder.createAndBindUi(this));
		roster = factory.getCurrentRoster();
		currrentClassTime = factory.getSelectedClassTime();
		container.add(factory.getClassTimeForm());

		if(currrentClassTime != null){
			
//			ClasstimeGridItem activeItem = new ClasstimeGridItem();
//			activeItem.setClassTime(currrentClassTime);
//			activeItem.addStyleName("activeClassTime");
//			cardCol.add(activeItem);
//			if(roster.getClassTimes().length() > 1){
//			for(int i = 0; i < roster.getClassTimes().length(); i++){
//				ClasstimeGridItem item = new ClasstimeGridItem();
//				item.setClassTime(roster.getClassTimes().get(i));
//				cardCol.add(item);
//			 }
//			}
		
		}
	}
	
	
	public void addItem(ClasstimeGridItem classtimeGridItem){
		  cardCol.add(classtimeGridItem);
		
	}
	
	public MaterialColumn getCardCol() {
		return this.cardCol;
	}


	public MaterialAnchorButton getCreateBtn() {
		return this.createBtn;
	}


	public MaterialColumn getContainer() {
		return this.cardCol;
	}


	public interface Presenter{
		void classTimeAddButtonClickEvent();
	}

}
