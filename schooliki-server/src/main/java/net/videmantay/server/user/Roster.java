package net.videmantay.server.user;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import net.videmantay.rest.dto.RosterDTO;
import net.videmantay.server.entity.ClassTime;
import net.videmantay.server.entity.Goal;
import net.videmantay.server.entity.GoogleService;
import net.videmantay.server.entity.Incident;
import net.videmantay.server.entity.SeatingChart;
import net.videmantay.server.entity.StudentGroup;
import net.videmantay.server.entity.StudentJob;
import net.videmantay.server.entity.TeacherInfo;
import net.videmantay.server.validation.ValidDateFormat;
import net.videmantay.server.validation.ValidDateRange;
import net.videmantay.shared.GradeLevel;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.Serialize;

@Cache
@Entity
@ValidDateRange
public class Roster implements Serializable{
	
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	@Index
	//@NotNull -when creating a new one this isn't set
	//i think of it as a security risk but maybe it isn't
	public transient String ownerId;
	
	@NotNull
	@SafeHtml
	public String title;
	
	@SafeHtml
	public String description;
	
	@SafeHtml
	public String roomNum;

	//@NotNull
	@Serialize
	public TeacherInfo teacherInfo;
	
	public GradeLevel gradeLevel;
	
	@NotNull
	public Date startDate;
	
	@NotNull
	public Date endDate;
	
	//all map to a spreadsheet
	@SafeHtml
	public String rollBook;
	@SafeHtml
	public String gradeBook;
	@SafeHtml
	public String behaviorReport;

	
	@Serialize
	public List<GoogleService> googleCalendars = new ArrayList<GoogleService>();
	
	@Serialize 
	public List<GoogleService> googleTasks = new ArrayList<GoogleService>();
	
	@Serialize
	public List<GoogleService> googleFolders = new ArrayList<GoogleService>();

	
	public Roster(){
		
	}

	public Long getId() {
		return id;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public TeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(TeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
	}
	
	public GradeLevel getGradeLevel(){
		return this.gradeLevel;
	}
	
	public void setGradeLevel(GradeLevel grdLvl){
		this.gradeLevel = grdLvl;
	}
	
	public List<GoogleService> getGoogleFolders(){
		return this.googleFolders;
	}
	
	public void setGoogleFolders(List<GoogleService> folders){
		this.googleFolders = folders;
		
	}
	
	public List<GoogleService> getGoogleCalendars(){
		return this.googleCalendars;
	}
	
	public void setGoogleCalendars(List<GoogleService> googleCals){
		this.googleCalendars = googleCals;
	}
	
	public List<GoogleService> getGoogleTasks(){
		return this.googleTasks;
	}
	
	public void setGoogleTasks(List<GoogleService> googleTasks){
		this.googleTasks = googleTasks;
	}

	public String getRollBook() {
		return rollBook;
	}

	public void setRollBook(String rollBook) {
		this.rollBook = rollBook;
	}

	public String getGradeBook() {
		return gradeBook;
	}

	public void setGradeBook(String gradeBook) {
		this.gradeBook = gradeBook;
	}

	public String getBehaviorReport() {
		return behaviorReport;
	}

	public void setBehaviorReport(String behaviorReport) {
		this.behaviorReport = behaviorReport;
	}


	public RosterDetail createDetail(){
		RosterDetail detail = new RosterDetail();
		detail.setDescription(this.description);
		detail.setTitle(this.title);
		detail.setGradeLevel(this.gradeLevel);
		detail.setTeacherInfo(this.teacherInfo);
		detail.setOwnerId(this.getOwnerId());
		return detail;
	}
	

	
	public static Roster createFromDTO(RosterDTO dto) throws ParseException{
		
		SimpleDateFormat sDateFormat = new SimpleDateFormat("YYYY-mm-dd");
		
		Roster roster = new Roster();
		
		roster.id = dto.id;
		roster.ownerId = dto.ownerId;
		roster.title = dto.title;
		roster.description = dto.description;
		roster.roomNum = dto.roomNum;
		roster.teacherInfo = dto.teacherInfo;
		roster.gradeLevel = dto.gradeLevel;
		roster.startDate = sDateFormat.parse(dto.startDate);
		roster.endDate = sDateFormat.parse(dto.endDate);
		roster.rollBook = dto.rollBook;
		roster.gradeBook = dto.gradeBook;
		roster.behaviorReport = dto.behaviorReport;
		roster.googleCalendars = dto.googleCalendars;
		roster.googleTasks = dto.googleTasks;
		roster.googleFolders = dto.googleFolders;
		
		 return roster;
	}


	
}
