package net.videmantay.roster.views.classtime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.RosterJson;

public class ClassTimeGrid extends Composite {

	private final RosterUtils utils;
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

	
	public ClassTimeGrid(RosterUtils ru) {
		this.utils = ru;
		initWidget(uiBinder.createAndBindUi(this));
		roster = utils.getCurrentRoster();
		currrentClassTime = utils.getSelectedClassTime();
		
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
