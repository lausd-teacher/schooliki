package net.videmantay.admin.views;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

import gwt.material.design.client.ui.MaterialLoader;
import net.videmantay.admin.json.AppUserJson;

public class AppUserAsyncDataProvider extends AsyncDataProvider<AppUserJson> {
	private final ArrayList<AppUserJson> list = new ArrayList<AppUserJson>();

	@Override
	protected void onRangeChanged(final HasData<AppUserJson> display) {
		Ajax.get("/appuser").done(new Function() {
			@Override
			public void f() {
				GWT.log(this.arguments(0).toString());
				JsArray<AppUserJson> array = JsonUtils.unsafeEval((String)this.arguments(0)).cast();
				 for(int i = 0; i < array.length(); i++){
					 list.add(array.get(i));
				 }//end for
				 display.setRowCount(list.size());
				display.setRowData(0, list); 
				MaterialLoader.showLoading(false);
			}
		}).progress(new Function() {
			@Override
			public void f() {
				MaterialLoader.showLoading(true);
			}
		}).fail(new Function() {
			@Override
			public void f() {
				MaterialLoader.showLoading(false);
				Window.alert("Error connecting to the Server, Please try again later");
			}
		});
	}
	
	@Override
	public Long getKey(AppUserJson item){
		return item.getId();
	}

}
