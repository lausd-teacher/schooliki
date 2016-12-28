package net.videmantay.roster.views.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


public class AssignementTable extends Composite {

	private static AssignementTableUiBinder uiBinder = GWT.create(AssignementTableUiBinder.class);

	interface AssignementTableUiBinder extends UiBinder<Widget, AssignementTable> {
	}
	
	

	public AssignementTable() {
		initWidget(uiBinder.createAndBindUi(this));
		setUpTable();
	}
	
	
	public void setUpTable(){
	
		
	}

}
