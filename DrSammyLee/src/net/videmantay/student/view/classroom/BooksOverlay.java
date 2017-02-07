package net.videmantay.student.view.classroom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BooksOverlay extends Composite {

	private static BooksOverlayUiBinder uiBinder = GWT.create(BooksOverlayUiBinder.class);

	interface BooksOverlayUiBinder extends UiBinder<Widget, BooksOverlay> {
	}

	public BooksOverlay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
