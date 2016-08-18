package net.videmantay.server.user;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialize;

import net.videmantay.server.entity.Goal;
import net.videmantay.server.entity.StudentJob;


@Entity
public class RosterStudent extends DBObj implements  Serializable,Comparator<RosterStudent> {
	
	@Id
	public Long id;
	///Key to roster detail not parent roster
	//why not set actual key???
	public Long roster;
	public String firstName;
	public String lastName;
	public String extName;
	public Thumbnail[] thumbnails;
	public Date DOB;
	public Boolean glasses;
	public String eldLevel;
	public Set<StudentJob> jobs;
	public boolean inactive = false;
	
	@Index
	public String acctId;
	
	public int[] points; 
	
	@Serialize
	public Set<Goal> goals;

	@Parent
	public transient Key<Roster> rosterKey;
	
	@Ignore
	public RosterDetail rosterDetail;
	
	public String studentFolderId;
	public String studentCalId;
	public String recordUrl;
	public String studentTasksId;
	
	public RosterStudent(){
		
	}
	public Long getRoster() {
		return roster;
	}
	public void setRoster(Long roster) {
		this.roster = roster;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Boolean getGlasses() {
		return glasses;
	}
	public void setGlasses(Boolean glasses) {
		this.glasses = glasses;
	}
	public String getEldLevel() {
		return eldLevel;
	}
	public void setEldLevel(String eldLevel) {
		this.eldLevel = eldLevel;
	}
	public Set<StudentJob> getJobs() {
		return jobs;
	}
	public void setJobs(Set<StudentJob> jobs) {
		this.jobs = jobs;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getExtName(){
		return this.extName;
	}
	public void setExtName(String extName){
		this.extName = extName;
	}
	public void setPicUrl(Thumbnail[] thumbnails) {
		this.thumbnails = thumbnails;
	}
	
	public Thumbnail[] getThumbnails(){
		return this.thumbnails;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String studentGoogleId) {
		this.acctId = studentGoogleId;
	}

	
	public Set<Goal> getBadges() {
		return goals;
	}
	public void setBadges(Set<Goal> goals) {
		this.goals = goals;
	}
	public Long getId() {
		return id;
	}
	
	public Key<Roster> getRosterKey() {
		return rosterKey;
	}
	public void setRosterKey(Key<Roster> rosterKey) {
		this.rosterKey = rosterKey;
	}
	public RosterDetail getRosterDetail(){
		return this.rosterDetail;
	}
	
	public void setRostserDetail(RosterDetail rosDe){
		this.rosterDetail = rosDe;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStudentFolderId() {
		return studentFolderId;
	}
	public void setStudentFolderId(String studentFolderId) {
		this.studentFolderId = studentFolderId;
	}
	public String getStudentCalId() {
		return studentCalId;
	}
	public void setStudentCalId(String studentCalId) {
		this.studentCalId = studentCalId;
	}
	public String getStudentTasksId() {
		return studentTasksId;
	}
	
	public String getRecordUrl(){
		return this.recordUrl;
	}
	
	public void setRecordUrl(String url){
		this.recordUrl = url;
	}
	public void setStudentTasksId(String studentTasksId) {
		this.studentTasksId = studentTasksId;
	}
	public Boolean getInactive() {
		return inactive;
	}
	public void setInactive(Boolean inactive) {
		this.inactive = inactive;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DOB == null) ? 0 : DOB.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((roster == null) ? 0 : roster.hashCode());
		result = prime * result
				+ ((acctId == null) ? 0 : acctId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RosterStudent))
			return false;
		RosterStudent other = (RosterStudent) obj;
		if (DOB == null) {
			if (other.DOB != null)
				return false;
		} else if (!DOB.equals(other.DOB))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (roster == null) {
			if (other.roster != null)
				return false;
		} else if (!roster.equals(other.roster))
			return false;
		if (acctId == null) {
			if (other.acctId != null)
				return false;
		} else if (!acctId.equals(other.acctId))
			return false;
		return true;
	}
	@Override
	public int compare(RosterStudent student1, RosterStudent student2) {
		
		
		switch(student1.getLastName().compareToIgnoreCase(student2.getLastName())){
			case 0: compareFirstName(student1, student2)  ;break;
			case 1: return 1;
			case -1: return -1;
		}
		return 0;
	}
	
	private int compareFirstName(RosterStudent student1, RosterStudent student2){
		switch(student1.getFirstName().compareToIgnoreCase(student2.getFirstName())){
		case 0: compareDOB(student1, student2) ;break;
		case 1: return 1;
		case -1: return -1;
		
		}
		return 0;
		
	}
	
	private int compareDOB(RosterStudent student1, RosterStudent student2){
		
		switch(student1.getDOB().compareTo(student2.getDOB())){
		case 0: compareId(student1, student2); break;
		case 1: return 1;
		case -1: return -1;
		}
		
		return 0;
	}
	
	private int compareId(RosterStudent student1, RosterStudent student2){
		switch(student1.getId().compareTo(student2.getId())){
		case 1: return 1;
		case -1: return -1;
		case 0: return 0;
		default: return 0;
		
		}
	}
	@Override
	public boolean valid() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
