package net.videmantay.roster.views.draganddrop;

import com.google.gwt.dom.client.DivElement;

public class FurnitureRemoveAction implements Action {
	
	DivElement item;
	
	public FurnitureRemoveAction(DivElement item) {
		this.item = item;
	}

	@Override
	public void exec() {
		item.removeFromParent();
	}

	public DivElement getItem() {
		return this.item;
	}

}
