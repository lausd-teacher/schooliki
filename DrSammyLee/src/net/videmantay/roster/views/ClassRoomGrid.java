package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialModal;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.views.StudentActionModal;
import net.videmantay.roster.views.student.CreateStudentForm;

public class ClassRoomGrid extends Composite implements HasRosterDashboardView {

	private static ClassRoomGridUiBinder uiBinder = GWT.create(ClassRoomGridUiBinder.class);

	interface ClassRoomGridUiBinder extends UiBinder<Widget, ClassRoomGrid> {
	}
	
	@UiField
	MaterialContainer container;
	
	private final StudentActionModal stuModal;
	private final CreateStudentForm createStudentFrom;
	private MaterialFAB fab = new MaterialFAB();
	private MaterialAnchorButton addButton = new MaterialAnchorButton(ButtonType.FLOATING);

    
	public ClassRoomGrid(CreateStudentForm form, StudentActionModal modal) {
		initWidget(uiBinder.createAndBindUi(this));
		this.stuModal = modal;
		this.createStudentFrom = form;
		addButton.setIconType(IconType.ADD);
		addButton.setIconSize(IconSize.LARGE);
		addButton.setIconFontSize(2, Unit.EM);
		fab.add(addButton);
		container.add(fab);

		container.add(stuModal);
		container.add(createStudentFrom);
		
	}

	@Override
	public void checkHW() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void groups() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickRandom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multipleSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void home() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unHome() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrangeFurniture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrangeStudents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneCheckHW() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneGroups() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneTakeRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void donePickRandom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneSelectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel(String state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneMultipleSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneArrangeFurniture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneArrangeStudents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneManageStations() {
		// TODO Auto-generated method stub
		
	}
	
}
