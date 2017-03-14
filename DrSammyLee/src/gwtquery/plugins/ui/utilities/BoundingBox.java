package gwtquery.plugins.ui.utilities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class BoundingBox extends JavaScriptObject {
	protected BoundingBox(){}
	

	
	public static final native double left(Element e)/*-{
		var el = $wnd.jQuery(e);
	var s = el.css('transform');
	var stringArray = s.match(/[0-9., -]+/)[0].split(", "); // results in ["0.312321", "-0.949977", "0.949977", "0.312321", "0", "0"]
	console.log(stringArray);
	var a = parseFloat(stringArray[0]);
	var b = parseFloat(stringArray[1]);
	var c = parseFloat(stringArray[2]);
	var d = parseFloat(stringArray[3]);
	
	   return a *el.offset().left + c *  el.offset().top + 0;
}-*/;
	
	public static final native double top(Element e)/*-{
		var el = $wnd.jQuery(e);
	var s = el.css('transform');
	var stringArray = s.match(/[0-9., -]+/)[0].split(", "); // results in ["0.312321", "-0.949977", "0.949977", "0.312321", "0", "0"]
	var a = parseFloat(stringArray[0]);
	var b = parseFloat(stringArray[1]);
	var c = parseFloat(stringArray[2]);
	var d = parseFloat(stringArray[3]);
	return b * el.offset().left + d *  el.offset().top + 0;
}-*/;
	
}
