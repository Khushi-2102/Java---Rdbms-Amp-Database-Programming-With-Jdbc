package DB;
import model.Student;
import util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.DatabaseConnection;

public class StudentDAO {
	public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, email, phone, address) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getAddress());
            
            int rows = stmt.executeUpdate();
            Logger.logOperation("ADD_STUDENT", "Added student: " + student.getName());
            return rows > 0;
            
        } catch (SQLException e) {
            Logger.logError("ADD_STUDENT", e.getMessage());
            return false;
        }
    }
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            Logger.logError("GET_ALL_STUDENTS", e.getMessage());
        }
        
        return students;
    }
    
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        Student student = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
            }
            
        } catch (SQLException e) {
            Logger.logError("GET_STUDENT_BY_ID", e.getMessage());
        }
        
        return student;
    }
    
    public List<Student> searchStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            Logger.logError("SEARCH_STUDENTS_BY_NAME", e.getMessage());
        }
        
        return students;
    }
    
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, phone = ?, address = ? WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getAddress());
            stmt.setInt(5, student.getStudentId());
            
            int rows = stmt.executeUpdate();
            Logger.logOperation("UPDATE_STUDENT", "Updated student ID: " + student.getStudentId());
            return rows > 0;
            
        } catch (SQLException e) {
            Logger.logError("UPDATE_STUDENT", e.getMessage());
            return false;
        }
    }
    
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            int rows = stmt.executeUpdate();
            Logger.logOperation("DELETE_STUDENT", "Deleted student ID: " + studentId);
            return rows > 0;
            
        } catch (SQLException e) {
            Logger.logError("DELETE_STUDENT", e.getMessage());
            return false;
        }
    }
}
