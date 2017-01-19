package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.roster.json.JoinRequestJson;

public class JoinRequestItem extends Composite {
	
	@UiField
	public MaterialCollectionItem item;

	@UiField
	public MaterialImage itemImg;
	
	@UiField
	public MaterialLabel itemLabel;
	
	@UiField
	public MaterialAnchorButton acceptRequestBtn;
	
	@UiField
	public MaterialAnchorButton dismissRequestBtn;

	private static JoinRequestItemUiBinder uiBinder = GWT.create(JoinRequestItemUiBinder.class);

	interface JoinRequestItemUiBinder extends UiBinder<Widget, JoinRequestItem> {
	}

	public JoinRequestItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setData(JoinRequestJson jr){
		itemImg.setUrl(jr.getPicUrl());
		itemLabel.setText(jr.getEmail());
	}
	
}
