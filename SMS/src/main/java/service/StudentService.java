package service;

import DB.StudentDAO;
import model.Student;
import java.util.List;
import java.util.Scanner;
public class StudentService {
	private StudentDAO studentDAO;
    private Scanner scanner;
    
    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.scanner = new Scanner(System.in);
    }
    
    public void addStudent() {
        System.out.println("\n=== Add New Student ===");
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        Student student = new Student(name, email, phone, address);
        
        if (studentDAO.addStudent(student)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student.");
        }
    }
    
    public void viewAllStudents() {
        System.out.println("\n=== All Students ===");
        List<Student> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }
    
    public void searchStudent() {
        System.out.println("\n=== Search Student ===");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by name");
        System.out.print("Choose option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                System.out.print("Enter student ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                
                Student student = studentDAO.getStudentById(id);
                if (student != null) {
                    System.out.println("Student found: " + student);
                } else {
                    System.out.println("Student not found.");
                }
                break;
                
            case 2:
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                
                List<Student> students = studentDAO.searchStudentsByName(name);
                if (students.isEmpty()) {
                    System.out.println("No students found.");
                } else {
                    students.forEach(System.out::println);
                }
                break;
                
            default:
                System.out.println("Invalid option.");
        }
    }
    
    public void updateStudent() {
        System.out.println("\n=== Update Student ===");
        System.out.print("Enter student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Current details: " + student);
        
        System.out.print("Enter new name (current: " + student.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) student.setName(name);
        
        System.out.print("Enter new email (current: " + student.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) student.setEmail(email);
        
        System.out.print("Enter new phone (current: " + student.getPhone() + "): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) student.setPhone(phone);
        
        System.out.print("Enter new address (current: " + student.getAddress() + "): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) student.setAddress(address);
        
        if (studentDAO.updateStudent(student)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Failed to update student.");
        }
    }
    
    public void deleteStudent() {
        System.out.println("\n=== Delete Student ===");
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Are you sure you want to delete student: " + student.getName() + "? (y/n)");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            if (studentDAO.deleteStudent(id)) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }
}
