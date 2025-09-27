package DB; // Assuming 'DB' is the package name

import model.Course;
import util.Logger; // Assuming your logger class is named LoggerUtil
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import dao.DatabaseConnection; 

public class CourseDAO {

   
    public boolean addCourse(Course course) {

        boolean success = false;
        
        String sql = "INSERT INTO courses (id,course_name, fees, duration_months) VALUES (?,?, ?, ?)";

        // FIX: Use DBConnection.createConnection() for consistency
        try (Connection conn = DatabaseConnection.getConnection(); 
             // FIX: Correct method name and syntax, removing the extra semicolon
             PreparedStatement pst = conn.prepareStatement(sql)) { 
            pst.setString(1,course.getCourseId());
            pst.setString(2, course.getCourseName());
            pst.setDouble(3, course.getFees());
            pst.setInt(4, course.getDurationMonths());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                success = true;
                Logger.log("COURSE_ADD: Added course: " + course.getCourseName());
            } else {
                Logger.log("COURSE_ADD: Failed to add course (0 rows affected): " + course.getCourseName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    // --- 2. Corrected getAllCourses method ---
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT course_id, course_name, fees, duration_months FROM courses";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

        	while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getString("courseId"));
                course.setCourseName(rs.getString("course_name"));
                course.setFees(rs.getDouble("fees"));
                course.setDurationMonths(rs.getInt("duration_months"));
                courses.add(course);
            }

        } catch (SQLException e) {
            
        }
        return courses;
    }

  
    public boolean updateCourse(Course course) {
       
        Logger.log("COURSE_UPDATE: Attempted update for course: " + course.getCourseName());
        return false;
    }

   
    public boolean deleteCourse(int id) {
        
        Logger.log("COURSE_DELETE: Attempted delete for course ID: " + id);
        return false;
    }

    // --- 5. Get Course by ID (Placeholder for implementation) ---
    public Course getCourseById(int id) {
        // Implementation logic goes here...
        return null;
    }

}