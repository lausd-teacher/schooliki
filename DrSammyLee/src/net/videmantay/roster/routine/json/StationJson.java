package net.videmantay.roster.routine.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

import net.videmantay.student.json.RosterStudentJson;

public class StationJson extends JavaScriptObject {
	protected StationJson(){}

	public final native String getColor()/*-{
		return this.color;
	}-*/;
	
	public final native String getTitle()/*-{
		return this.title;
	}-*/;
	
	public final native String getDescription()/*-{
		return this.description;
	}-*/;
	
	public final native String getType()/*-{
		return this.type;
	}-*/;
	
	public final native String getZIndex()/*-{
		return this.zIndex;
	}-*/;
	
	public final native String getX()/*-{
	return  this.x;
}-*/;
	
	public final native String getY()/*-{
	return this.y;
}-*/;
	
	public final native String getWidth()/*-{
	return this.width;
}-*/;
	
	public final native String getHeight()/*-{
	return this.height;
}-*/;
	
	public final native String getRotate()/*-{
	return this.rotate;
}-*/;
	
	public final native int getNumber()/*-{
				return this.number;
	}-*/;
	
	public final native JsArrayString getStudentIds()/*-{
		return this.studentIds;
	}-*/;
	

	/////////SETTERS///////////////////////////////////////////////////
	public final native StationJson setColor(String color)/*-{
			this.color = color;
			return this;
	}-*/;
	
	public final native StationJson setTitle(String name)/*-{
			this.title = name;
			return this;
	}-*/;
	
	public final native StationJson setDescription(String name)/*-{
	this.description = name;
	return this;
}-*/;
	
	public final native StationJson setOffset(JavaScriptObject offset)/*-{
			this.offset = offset;
			return this;
	}-*/;
	
	public final native StationJson setType(String type)/*-{
			this.type= type;
			return this;
	}-*/;
		
	public final native StationJson setStudents(JsArrayString students)/*-{
				this.students = students;
				return this;
	}-*/;
	
	public final native StationJson setNum(int number)/*-{
				this.number = number;
				return this;
	}-*/;
	
	public final native StationJson setWidth(String type)/*-{
	this.width= type;
	return this;
}-*/;
	
	public final native StationJson setHeight(String type)/*-{
	this.height= type;
	return this;
}-*/;
	
	public final native StationJson setX(String type)/*-{
	this.x= type;
	return this;
}-*/;
	
	public final native StationJson setY(String type)/*-{
	this.y= type;
	return this;
}-*/;
	
	public final native StationJson setRotate(String type)/*-{
	this.rotate= type;
	return this;
}-*/;
	
	public final native StationJson setImage(String type)/*-{
	this.image= type;
	return this;
}-*/;
	
	public final native StationJson setStudent(JsArrayString studentIds)/*-{
		this.studentIds = studentIds;
	}-*/;
	
	public final StationJson copy(){
		StationJson copy = StationJson.createObject().cast();
		copy.setColor(this.getColor());
		copy.setTitle(this.getTitle());
		copy.setDescription(this.getDescription());
		copy.setHeight(this.getHeight());
		copy.setNum(this.getNumber());
		copy.setWidth(this.getWidth());
		copy.setX(this.getX());
		copy.setY(this.getY());
		return copy;
	}
	
	
}
