package net.videmantay.rest.dto;

import java.util.Date;

import net.videmantay.server.entity.ClassTime;

public class ClassTimeDTO {
	
	Long id;
	Long rosterId;
	String title;
	String descript;
	Date lastUpdate;
	String startTime;
	String endTime;
	
	boolean isDefault;

	public ClassTimeDTO(ClassTime classTime) {
		this.id = classTime.getId();
		this.rosterId = classTime.getRosterId();
		this.title = classTime.getTitle();
		this.descript = classTime.getDescript();
		this.lastUpdate = classTime.getLastUpdate();
		this.startTime = classTime.getStartTime();
		this.endTime = classTime.getEndTime();
		this.isDefault = classTime.isDefault();
	}
	
	public ClassTimeDTO(){
		
		
	}

	public Long getId() {
		return this.id;
	}

	public Long getRosterId() {
		return this.rosterId;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescript() {
		return this.descript;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean getIsDefault() {
		return this.isDefault;
	}
	
	public void setIsDefault(boolean isDefault){
		this.isDefault = isDefault;
	}

}
