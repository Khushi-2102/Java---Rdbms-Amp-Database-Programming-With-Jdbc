package model;

public class Course {
    
    private String courseId,courseName;
    private double fees;
    private int durationMonths;
    
    
    public Course () {};
    
    public Course(String courseName, double fees, int durationMonths) {
        this.courseName = courseName;
        this.fees = fees;
        this.durationMonths = durationMonths;
    }
    
	// Getters and setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public double getFees() { return fees; }
    public void setFees(double fees) { this.fees = fees; }
    
    public int getDurationMonths() { return durationMonths; }
    public void setDurationMonths(int durationMonths) { this.durationMonths = durationMonths; }
    
    @Override
    public String toString() {
        return "Course ID: " + courseId + ", Name: " + courseName + 
               ", Fees: $" + fees + ", Duration: " + durationMonths + " months";
    }
}
