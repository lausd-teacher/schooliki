package net.videmantay.roster;

 public class  RosterUrl {
	 private final static String roster = "/roster/";
	 private final static String assignment = "assignment/";
	 private final static String student = "student/";
	 private final static String classtime = "classtime/";
	 private final static String schedule = "schedule/";
	 private final static String seatingChart = "seatingchart/";
	 
	public final static String roster(){return roster;}
	public final static String roster(Long id){return roster + id;}
	
	public final static String student(Long id){return roster + id + student;}
	public final static String  student(Long id, String studentId){return roster + id + student + studentId;}
	
	public final static String classtime(Long id){return roster + id + classtime;}
	public final static String classtime(Long id, Long classtimeId){return roster+ id+ classtime + classtimeId;}
	
}
