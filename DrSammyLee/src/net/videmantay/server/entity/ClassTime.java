package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.rest.dto.ClassTimeDTO;
import net.videmantay.rest.dto.RosterStudentDTO;
import net.videmantay.server.user.RosterStudent;

@Entity
public class ClassTime implements Serializable{
	
	private static final long serialVersionUID = -2845019435811983254L;
	
	
	@Id
	public Long id;
	
	@Index
	public Long rosterId;
	public String title;
	public String descript;
	public Date lastUpdate;
	
	public String startTime;
	public String endTime;
	
	public boolean isDefault;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getRosterId() {
		return this.rosterId;
	}
	public String getStartTime() {
		return this.startTime;
	}
	public String getEndTime() {
		return this.endTime;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassTime other = (ClassTime) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	public static ClassTime createFromDTO(ClassTimeDTO dto){
		ClassTime classtime = new ClassTime();
		classtime.id = dto.getId();
		classtime.rosterId = dto.getRosterId();
		classtime.title = dto.getTitle();
		classtime.descript = dto.getDescript();
		classtime.lastUpdate = dto.getLastUpdate();
		classtime.startTime = dto.getStartTime();
		classtime.endTime = dto.getEndTime();
		classtime.isDefault = dto.getIsDefault();
		
		return classtime;
	}
	public boolean isDefault() {
		return this.isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	

}
