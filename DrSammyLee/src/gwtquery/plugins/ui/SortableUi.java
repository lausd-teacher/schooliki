package gwtquery.plugins.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class SortableUi extends JavaScriptObject {
	public native final SortableUi create()/*-{
			return {};
	}-*/;
	
	public native final Element helper()/*-{
		return this['helper'].get(0);
	}-*/;
	
	public native final Element item()/*-{
			return this['item'].get(0);
	}-*/;

	public native final Element placeholder()/*-{
			return this['placeholder'].get(0);
	}-*/;
}
