package DB;
import model.Enrollment;
import util.Logger;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.DatabaseConnection;
public class EnrollmentDAO {
	public boolean enrollStudent(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setDate(3, enrollment.getEnrollmentDate());
            
            int rows = stmt.executeUpdate();
            Logger.logOperation("ENROLL_STUDENT", 
                "Student ID: " + enrollment.getStudentId() + " enrolled in Course ID: " + enrollment.getCourseId());
            return rows > 0;
            
        } catch (SQLException e) {
            Logger.logError("ENROLL_STUDENT", e.getMessage());
            return false;
        }
    }
    
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT e.*, s.name as student_name, c.course_name " +
                    "FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.student_id " +
                    "JOIN courses c ON e.course_id = c.course_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getDate("enrollment_date"));
                enrollment.setStudentName(rs.getString("student_name"));
                enrollment.setCourseName(rs.getString("course_name"));
                enrollments.add(enrollment);
            }
            
        } catch (SQLException e) {
            Logger.logError("GET_ALL_ENROLLMENTS", e.getMessage());
        }
        
        return enrollments;
    }
    
    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT e.*, s.name as student_name, c.course_name " +
                    "FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.student_id " +
                    "JOIN courses c ON e.course_id = c.course_id " +
                    "WHERE e.student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getDate("enrollment_date"));
                enrollment.setStudentName(rs.getString("student_name"));
                enrollment.setCourseName(rs.getString("course_name"));
                enrollments.add(enrollment);
            }
            
        } catch (SQLException e) {
            Logger.logError("GET_ENROLLMENTS_BY_STUDENT", e.getMessage());
        }
        
        return enrollments;
    }
    
    public boolean cancelEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enrollmentId);
            int rows = stmt.executeUpdate();
            Logger.logOperation("CANCEL_ENROLLMENT", "Canceled enrollment ID: " + enrollmentId);
            return rows > 0;
            
        } catch (SQLException e) {
            Logger.logError("CANCEL_ENROLLMENT", e.getMessage());
            return false;
        }
    }
}

