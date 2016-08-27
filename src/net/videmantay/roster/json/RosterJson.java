package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.shared.GradeLevel;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.student.json.StudentGroupJson;
import net.videmantay.student.json.StudentJobJson;
import net.videmantay.student.json.TeacherInfoJson;

public class RosterJson extends JavaScriptObject {

	protected RosterJson(){}

	
	public final  native StudentJobJson[] getStudentJobs()/*-{
		return this.studentJobs;
	}-*/;

	public final native RosterJson setStudentJobs(StudentJobJson[] studentJobs)/*-{
		this.studentJobs = studentJobs;
		return this;
	}-*/;

	public final native Long getId()/*-{
		return this.id;
	}-*/;

	public final native String getOwnerId()/*-{
		return this.ownerId;
	}-*/;

	public final native RosterJson setOwnerId(String ownerId)/*-{
		this.ownerId = ownerId;
		return this;
	}-*/;

	public final native String getStartDate()/*-{
		return this.startDate;
	}-*/;

	public final native RosterJson setStartDate(String startDate)/*-{
		this.startDate = startDate;
		return this;
	}-*/;

	public final native String getEndDate()/*-{
		return this.endDate;
	}-*/;

	public final native RosterJson setEndDate(String endDate)/*-{
		this.endDate = endDate;
		return this;
	}-*/;

	public final native String getTitle()/*-{
		return this.title;
	}-*/;

	public final native RosterJson setTitle(String title)/*-{
		this.title = title;
		return this;
	}-*/;

	public final native String getDescription()/*-{
		return this.description;
	}-*/;

	public final native RosterJson setDescription(String description)/*-{
		this.description = description;
		return this;
	}-*/;

	public final native  String getRoomNum()/*-{
		return this.roomNum;
	}-*/;

	public final native RosterJson setRoomNum(String roomNum)/*-{
		this.roomNum = roomNum;
		return this;
	}-*/;

	public final native TeacherInfoJson getTeacherInfo()/*-{
		return this.teacherInfo;
	}-*/;

	public final native RosterJson setTeacherInfo(TeacherInfoJson teacherInfo)/*-{
		this.teacherInfo = teacherInfo;
		return this;
	}-*/;

	public final native JsArray<StudentGroupJson> getStudentGroups()/*-{
		return this.studentGroups;
	}-*/;

	public final native RosterJson setStudentGroups(JsArray<StudentGroupJson> studentGroups)/*-{
		this.studentGroups = studentGroups;
		return this;
	}-*/;
	
	public final native GradeLevel getGradeLevel()/*-{
		return this.gradeLevel;
	}-*/;
	
	public final native RosterJson setGradeLevel(GradeLevel grdLvl)/*-{
		this.gradeLevel = grdLvl;
		return this;
	}-*/;
	
	public final native JsArray<GoogleServiceJson> getGoogleCalendars()/*-{
		return this.googleCalendars;
	}-*/;
	
	public final native RosterJson setGoogleCalendars(JsArray<GoogleServiceJson> googleCals)/*-{
		this.googleCalendars = googleCals;
		return this;
	}-*/;
	
	public final native JsArray<GoogleServiceJson> getGoogleTasks()/*-{
		return this.googleTasks;
	}-*/;
	
	public final native RosterJson setGoogleTasks(JsArray<GoogleServiceJson> googleTasks)/*-{
		this.googleTasks = googleTasks;
		return this;
	}-*/;
	
	public final native String getRosterFolderId()/*-{
		return this.rosterFolderId;
	}-*/;

	public final native RosterJson setRosterFolderId(String rosterFolderId)/*-{
		this.rosterFolderId = rosterFolderId;
		return this;
	}-*/;
	
	public final native String getStudentFolderId()/*-{
		return this.studentFolderId;
	}-*/;
	
	public final native void setStudentFolderId(String studentFolder)/*-{
		this.studentFolderId = studentFolder;
	}-*/;
	
	public final native JsArray<RosterStudentJson> getRosterStudents()/*-{
		return this.rosterStudents;
	}-*/;

	public final native RosterJson setRosterStudents(JsArray<RosterStudentJson> rosterStudents)/*-{
		this.rosterStudents = rosterStudents;
		return this;
	}-*/;

	public final native String getRollBook()/*-{
		return this.rollBook;
	}-*/;

	public final native RosterJson setRollBook(String rollBook)/*-{
		this.rollBook = rollBook;
		return this;
	}-*/;
	
	public final native String getGradeBook()/*-{
		return this.gradeBook;
	}-*/;

	public final native RosterJson setGradeBook(String gradeBook)/*-{
		this.gradeBook = gradeBook;
		return this;
	}-*/;
	
	public final native  JsArray<IncidentJson> getIncidents()/*-{
		return this.incidents;
	}-*/;
	
	public final native RosterJson setIncident(JsArray<IncidentJson> json)/*-{
		this.incidents = json;
		return this;
	}-*/;

	public final native String getBehaviorReport()/*-{
		return this.behaviorReport;
	}-*/;

	public final native RosterJson setBehaviorReport(String behaviorReport)/*-{
		this.behaviorReport = behaviorReport;
		return this;
	}-*/;
	
	public final native JsArray<ClassTimeJson> getClassTimes()/*-{
			return this.classTimes;
	}-*/;
	
	public final native RosterJson setClassTimes(JsArray<ClassTimeJson> classTimes)/*-{
						this.classTimes = classTimes;
	}-*/;
	
	public final native JsArray<ClassTimeJson> addSeatingChart(ClassTimeJson seatingChart)/*-{
				this.seatingCharts.push(seatingChart);
	}-*/;
	
	public final native JsArray<ClassTimeJson> removeSeatingChart(ClassTimeJson seatingChart)/*-{
							for(var i = 0; i< this.seatingCharts.length; i++){
							    if(seatingCharts[i].id == seatingChart.id){
							    seatingCharts.splice(i,1);	
							    }	
							}
							
							return this.seatingCharts;
	}-*/;
	
	public final ClassTimeJson getDefaultClassTime(){
		ClassTimeJson classTime = this.getClassTimes().get(0);
		for(int i = 0; i < this.getClassTimes().length(); i++){
			if(this.getClassTimes().get(i).getIsDefault()){
				classTime =  this.getClassTimes().get(i);
				break;
			}
		}
		return classTime;
	}
	
}
