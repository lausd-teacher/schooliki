package net.videmantay.roster.views.draganddrop;

import com.google.gwt.dom.client.DivElement;

public interface Action {

	public void exec();
	
	public DivElement getItem();
}
