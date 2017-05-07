package net.videmantay.student.json;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;

public class StudentAttendanceJson extends JavaScriptObject {

	
	protected StudentAttendanceJson(){}
	
	public final native Long getId()/*-{
	return this.id;
}-*/;
	
	public final native StudentAttendanceJson setStudentId(String studentId)/*-{
	 this.studentId = studentId;
	 return this;
}-*/;
	
	public final native String getStudentId()/*-{
	return this.studentId;
}-*/;
	
	public final native StudentAttendanceJson setDate(String date)/*-{
	 this.date = date;
	 return this;
}-*/;
	
	
	public final native String getDate()/*-{
	return this.date;
}-*/;
	
	public final native StudentAttendanceJson setStatus(String status)/*-{
	 this.status = status;
	 return this;
}-*/;
	
	public final native String getStatus()/*-{
	return this.status;
}-*/;
	
	public final native StudentAttendanceJson setArrival(String arrival)/*-{
	 this.arrival = arrival;
	 return this;
}-*/;
	
	public final native String getArrival()/*-{
	return this.arrival;
}-*/;
	
	public static StudentAttendanceJson today(String studentId){
		StudentAttendanceJson stuA = StudentAttendanceJson.createObject().cast();
		
		stuA.setStudentId(studentId);
		stuA.setDate(DateTimeFormat.getFormat("dd-MM-yyyy").format(new Date()));
		stuA.setStatus("PRESENT");
		return stuA;
	}
	
	
}
