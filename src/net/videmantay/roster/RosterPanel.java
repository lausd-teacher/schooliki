package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.query.client.Function;
import static gwtquery.plugins.ui.Ui.Ui;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import static com.google.gwt.query.client.GQuery.*;
import net.videmantay.student.json.RosterDetailJson;

public class RosterPanel extends Composite {

	private static RosterPanelUiBinder uiBinder = GWT.create(RosterPanelUiBinder.class);

	interface RosterPanelUiBinder extends UiBinder<Widget, RosterPanel> {
	}
	
	@UiField
	MaterialCard card;
	
	@UiField
	public MaterialCardContent cardContent;
	@UiField
	public MaterialCardTitle cardTitle;
	@UiField
	public MaterialLabel cardDescript;
	private RosterDetailJson rosterDetail;
	private MouseOverHandler mouseOver = new MouseOverHandler(){
		@Override
		public void onMouseOver(MouseOverEvent e){
			e.stopPropagation();
			$(card).addClass("z-depth-5", "rosterPanel-active")
			.find(".card-title").css("fontWeight", "500");
			
		}
	};
	private MouseOutHandler mouseOut = new MouseOutHandler(){
		@Override
		public void onMouseOut(MouseOutEvent e){
			e.stopPropagation();
			$(card).removeClass("z-depth-5", "rosterPanel-active")
			.find(".card-title").css("fontWeight", "300");
		}
	};
	public RosterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		this.addStyleName("rosterPanel");
	}
	
	public void setData(RosterDetailJson data){
		console.log("Set data called for " + data.getTitle());
		rosterDetail = data;
		cardTitle.setText(data.getTitle());
		cardDescript.setText(data.getDescription());	
		//set click functions now
	}
	
	@Override
	public void onLoad(){
		$(card).click(new Function(){
			@Override
			public boolean f(Event e){
				console.log("roster panel clicked");
				History.newItem("roster/" + rosterDetail.getId());
				return true;
			}
		});
		
		//change css of this to cursor pointer
		$(card).css("cursor", "pointer");
		card.addDomHandler(mouseOver, MouseOverEvent.getType());
		card.addDomHandler(mouseOut, MouseOutEvent.getType());
	}
	
}
