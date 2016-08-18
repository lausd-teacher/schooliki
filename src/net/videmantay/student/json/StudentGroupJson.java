package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import net.videmantay.shared.GroupingType;

public class StudentGroupJson extends JavaScriptObject {

	protected StudentGroupJson(){}
	
	public final native Long getId() /*-{
		return this.id;
	}-*/;

	

	public final native String getTitle() /*-{
		return this.title;
	}-*/;

	

	public final native String getObjective() /*-{
		return this.objective;
	}-*/;

	

	public final native GroupingType getType() /*-{
		return this.type;
	}-*/;

	

	public final native String getBackGroundColor() /*-{
		return this.backGroundColor;
	}-*/;

	

	public final native String getTextColor() /*-{
		return this.textColor;
	}-*/;

	

	public final native String getBorderColor() /*-{
		return this.borderColor;
	}-*/;

	
}
