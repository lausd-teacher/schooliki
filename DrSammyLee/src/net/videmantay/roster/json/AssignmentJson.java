package net.videmantay.roster.json;

import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import net.videmantay.shared.GradeLevel;

public class AssignmentJson extends JavaScriptObject {

	protected AssignmentJson(){}
	
	public final native Long getId()/*-{
		return this.id;
	}-*/;

	public final native void setId(Long id)/*-{
		this.id = id;
	}-*/;

	public final native String getTitle()/*-{
		return this.title;
	}-*/;

	public final native void setTitle(String title)/*-{
		this.title = title;
	}-*/;

	public final native Set<GradeLevel> getGradeLevels()/*-{
		return this.gradeLevels;
	}-*/;

	public final native void setGradeLevels(Set<GradeLevel> gradeLevels)/*-{
		this.gradeLevels = gradeLevels;
	}-*/;

	public final native String getMediaUrl()/*-{
		return this.mediaUrl;
	}-*/;

	public final native void setMediaUrl(String mediaUrl)/*-{
		this.mediaUrl = mediaUrl;
	}-*/;

	public final native String getDescription()/*-{
		return this.description;
	}-*/;

	public final native void setDescription(String description)/*-{
		this.description = description;
	}-*/;

	public final native String getSubject()/*-{
		return this.subject;
	}-*/;

	public final native void setSubject(String subject)/*-{
		this.subject = subject;
	}-*/;

}
