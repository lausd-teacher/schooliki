package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.RosterJson;

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
	
	private RosterPanel rosterPanel = this;
	
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
	
	private ClickHandler click = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			RosterJson roster = $(rosterPanel).data("roster", RosterJson.class);
			History.newItem("c/" + roster.getId());
			RosterUtils.setCurrentRoster(roster);
		}
	};
	
	private ClickHandler promptDelete = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			RosterJson roster = $(rosterPanel).data("roster", RosterJson.class);
			$(body).trigger("promptDelete", roster);
			console.log("Prompt delete click this should trigger with data of: ");
			console.log(roster);
		}
	};
	
	private ClickHandler updateForm = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			RosterJson roster = $(rosterPanel).data("roster", RosterJson.class);
			$(body).trigger("updateRosterForm",roster);
		}
	};
	
	
	
	
	
	public RosterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		this.addStyleName("rosterPanel");
		
		
		
		card.addDomHandler(mouseOver, MouseOverEvent.getType());
		card.addDomHandler(mouseOut, MouseOutEvent.getType());
		//cardTitle.addDomHandler(click, ClickEvent.getType());
		cardContent.addDomHandler(click, ClickEvent.getType());
	
	}
	
	public void setData(final RosterJson data){
		console.log("Set data called for " + data.getTitle());
		cardTitle.setText(data.getTitle());
		cardDescript.setText(data.getDescription());
		$(this).data("roster", data);
		//use the id as dropdown activator
		cardTitle.getIcon().setActivates("r-"+data.getId());
		final MaterialDropDown rosterDropdown = new MaterialDropDown();
		rosterDropdown.setActivator("r-"+data.getId());
		final MaterialLink deleteRosterLink = new MaterialLink();
		deleteRosterLink.setText("Delete");
		deleteRosterLink.setIconType(IconType.DELETE);
		deleteRosterLink.setIconPosition(IconPosition.LEFT);
		deleteRosterLink.setIconFontSize(0.9, Unit.EM);
		deleteRosterLink.setFontSize("0.5em");
		deleteRosterLink.addClickHandler(promptDelete);
		
		final MaterialLink updateRosterLink = new MaterialLink();
		updateRosterLink.setText("Edit");
		updateRosterLink.setIconType(IconType.EDIT);
		updateRosterLink.setIconPosition(IconPosition.LEFT);
		updateRosterLink.setIconFontSize(0.9, Unit.EM);
		updateRosterLink.setFontSize("0.5em");
		updateRosterLink.addClickHandler(updateForm);
		
		//color row
		MaterialRow row = new MaterialRow();
		row.setPadding(0);
		MaterialColumn col;
		for(final String s: RosterColors.colors){
			col = new MaterialColumn();
			col.setGrid("s1");
			col.setMargin(2);
			MaterialButton button = new MaterialButton();
			button.setBackgroundColor(s);
			button.setSize("20px", "20px");
			button.setPadding(3);
			button.setMarginRight(5);
			button.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent e){
					card.setBackgroundColor(s);
					cardContent.setTextColor(s);
					$(rosterPanel).data("roster",RosterJson.class).setColor(s);
				}
			});
			col.add(button);
			row.add(col);
		}
		MaterialLink colorLink = new MaterialLink();
		colorLink.add(row);
		
		rosterDropdown.add(updateRosterLink);
		rosterDropdown.add(deleteRosterLink);
		rosterDropdown.add(colorLink);
		rosterDropdown.setConstrainWidth(true);
		card.add(rosterDropdown);	
			
	}
	
	@Override
	public void onLoad(){
		$(card).css("minWidth","100px").css("minHeight", "75px");
		$(card).css("cursor", "pointer");
		$(cardTitle.getIcon()).css("zIndex", "10");
		$(card).find("span.card-title > span").click(new Function(){
			@Override
			public boolean f(Event e){
				e.stopPropagation();
				History.newItem("c/" + $(rosterPanel).data("roster", RosterJson.class).getId());
				return true;
			}
		});
		
	}

}
