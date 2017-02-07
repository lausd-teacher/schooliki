package net.videmantay.student.view.classroom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentProfilePage extends Composite {

	private static StudentProfilePageUiBinder uiBinder = GWT.create(StudentProfilePageUiBinder.class);

	interface StudentProfilePageUiBinder extends UiBinder<Widget, StudentProfilePage> {
	}

	public StudentProfilePage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
