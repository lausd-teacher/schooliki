package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentActionFeedback extends Composite {

	private static StudentActionFeedbackUiBinder uiBinder = GWT.create(StudentActionFeedbackUiBinder.class);

	interface StudentActionFeedbackUiBinder extends UiBinder<Widget, StudentActionFeedback> {
	}

	public StudentActionFeedback() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
