package net.videmantay.roster.views.draganddrop;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

public class FurnitureAddAction implements Action{
	
	DivElement item;

	public FurnitureAddAction(DivElement item) {
		this.item = item;
	}

	@Override
	public void exec() {
		Document.get().getElementById("flrPlan").appendChild(item);
	}

	@Override
	public DivElement getItem() {
		return this.item;
	}


}
