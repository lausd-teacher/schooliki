package net.videmantay.server.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class StudentAttendance {

@Id
public Long id;

@Index
public String studentId;


@JsonIgnore
@Parent
public transient Key<Attendance> parent;

@Index
public String date;

@Index
public AttendanceStatus status;

public String arrival;


enum AttendanceStatus{PRESENT, ABSENT, TARDY};
}
