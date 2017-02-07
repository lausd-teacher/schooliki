package net.videmantay.roster;

 public class  RosterUrl {
	 private final static String roster = "/roster/";
	 private final static String assignment = "assignment/";
	 private final static String student = "/student/";
	 private final static String incident = "/incident/";
	 private final static String goal = "/goal/";
	 private final static String classtime = "/classtime/";
	 private final static String schedule = "/schedule/";
	 private final static String seatingChart = "/seatingchart/";
	 private final static String rosterconfig = "/rosterconfig";
	 private final static String search = "student/search/";
	 
	public final static String roster(){return roster;}
	public final static String roster(String id){return roster + id;}
	
	public final static String rosterconfig(Long id){return roster +id + rosterconfig;}
	
	public final static String student(Long id){return roster + id + student;}
	public final static String  student(Long id, String studentId){return roster + id + student + studentId;}
	public final static String studentSearch(){return roster+search;}

	public final static String  studentIncident(Long id , String studentId){
		return roster + id +student + studentId + incident;
	}
	
	public final static String  studentIncident(Long id , String studentId, Long incidentId){
		return roster + id +student + studentId + incident + incidentId;
	}
	
	public final static String  studentAssignment(Long id , String studentId){
		return roster + id +student + studentId + assignment;
	}
	
	public final static String  studentAssignment(Long id , String studentId, Long assignmentId){
		return roster + id +student + studentId + assignment +  assignmentId;
	}
	
	public final static String  studentGoal(Long id , String studentId){
		return roster + id +student + studentId + goal;
	}
	
	public final static String  studentGoal(Long id , String studentId, Long goalId){
		return roster + id +student + studentId + goal + goalId;
	}
	
	public final static String classtime(Long id){return roster + id + classtime;}
	public final static String classtime(Long id, Long classtimeId){return roster+ id+ classtime + classtimeId;}

	public final static String seatingchart(Long id, Long classtimeId){return roster + id +classtime + classtimeId + seatingChart;}

	public final static String schedule(Long id){return roster + id + schedule;}
}
