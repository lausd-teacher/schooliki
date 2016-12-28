package net.videmantay.roster.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.views.draganddrop.SelectionManager;

public class IncidentFormIconInput extends Composite {

	private static IncidentFormIconInputUiBinder uiBinder = GWT.create(IncidentFormIconInputUiBinder.class);

	interface IncidentFormIconInputUiBinder extends UiBinder<Widget, IncidentFormIconInput> {
	}
	
	@UiField
	HTMLPanel iconsContainer;
	
	@UiField
	HTMLPanel selectedIconContainer;
	
	@UiField
	ImageElement selectedIcon;
	
	
	
	public IncidentFormIconInput() {
		initWidget(uiBinder.createAndBindUi(this));
		iconsContainer.getElement().getStyle().setDisplay(Display.NONE);
		iconsContainer.getElement().getStyle().setOverflow(Overflow.SCROLL);
		selectedIconContainer.getElement().setAttribute("style", "width:9rem; position: relative;margin:auto;");
		selectedIcon.setSrc("img/1.png");
	}
	
	
	public void setIcons(JsArray<IncidentTypeJson> incidentTypes){
		for(int i = 0; i < incidentTypes.length(); i++){
			IncidentTypeJson incidentType = incidentTypes.get(i);
			IncidentFormIcon formIcon = new IncidentFormIcon(incidentType.getImageUrl());
			iconsContainer.add(formIcon);
			
			
			Event.sinkEvents(formIcon.getIncidentIcon(), Event.ONCLICK);
			Event.setEventListener(formIcon.getIncidentIcon(), new EventListener(){
				@Override
				public void onBrowserEvent(Event event) {
			          ImageElement el = event.getEventTarget().cast();
			         selectedIcon.setSrc(el.getSrc());
			        SelectionManager.unselectCurrentlySelectedIncidentType();
					SelectionManager.selectIncidentType(el);
					iconsContainer.getElement().getStyle().setDisplay(Display.NONE);
					iconsContainer.getElement().removeClassName("iconFormInputContainer");
				}
			});
		}
	}
	
	
	public ImageElement getSelectedIcon() {
		return this.selectedIcon;
	}


	public HTMLPanel getIconsContainer() {
		return this.iconsContainer;
	}


	public HTMLPanel getSelectedIconContainer() {
		return this.selectedIconContainer;
	}


	public interface Presenter{
		void incidentFormSelectedIconClickEvent();
	}

}
