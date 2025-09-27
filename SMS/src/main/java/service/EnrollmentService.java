package service;

import DB.EnrollmentDAO;
import DB.StudentDAO;
import DB.CourseDAO;
import model.Enrollment;
import model.Student;
import model.Course;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class EnrollmentService {
	private EnrollmentDAO enrollmentDAO;
	private StudentDAO studentDAO;
	private CourseDAO courseDAO;
	private Scanner scanner;

	public EnrollmentService() {
		this.enrollmentDAO = new EnrollmentDAO();
		this.studentDAO = new StudentDAO();
		this.courseDAO = new CourseDAO();
		this.scanner = new Scanner(System.in);
	}

	public void enrollStudent() {
		System.out.println("\n=== Enroll Student in Course ===");

		System.out.print("Enter student ID: ");
		int studentId = scanner.nextInt();

		System.out.print("Enter course ID: ");
		int courseId = scanner.nextInt();
		scanner.nextLine();

		// Validate student and course exist
		Student student = studentDAO.getStudentById(studentId);
		Course course = courseDAO.getCourseById(courseId);

		if (student == null) {
			System.out.println("Student not found.");
			return;
		}

		if (course == null) {
			System.out.println("Course not found.");
			return;
		}

		Enrollment enrollment = new Enrollment(studentId, courseId, new Date(System.currentTimeMillis()));

		if (enrollmentDAO.enrollStudent(enrollment)) {
			System.out.println("Student enrolled successfully!");
		} else {
			System.out.println("Failed to enroll student.");
		}
	}

	public void viewAllEnrollments() {
		System.out.println("\n=== All Enrollments ===");
		List<Enrollment> enrollments = enrollmentDAO.getAllEnrollments();

		if (enrollments.isEmpty()) {
			System.out.println("No enrollments found.");
		} else {
			enrollments.forEach(System.out::println);
		}
	}

	public void viewStudentEnrollments() {
		System.out.println("\n=== Student Enrollments ===");
		System.out.print("Enter student ID: ");
		int studentId = scanner.nextInt();
		scanner.nextLine();

		Student student = studentDAO.getStudentById(studentId);
		if (student == null) {
			System.out.println("Student not found.");
			return;
		}

		List<Enrollment> enrollments = enrollmentDAO.getEnrollmentsByStudent(studentId);

		System.out.println("Enrollments for student: " + student.getName());
		if (enrollments.isEmpty()) {
			System.out.println("No enrollments found.");
		} else {
			enrollments.forEach(System.out::println);
		}
	}

	public void cancelEnrollment() {
		System.out.println("\n=== Cancel Enrollment ===");
		System.out.print("Enter enrollment ID to cancel: ");
		int enrollmentId = scanner.nextInt();
		scanner.nextLine();

		List<Enrollment> allEnrollments = enrollmentDAO.getAllEnrollments();
		Enrollment targetEnrollment = allEnrollments.stream().filter(e -> e.getEnrollmentId() == enrollmentId)
				.findFirst().orElse(null);

		if (targetEnrollment == null) {
			System.out.println("Enrollment not found.");
			return;
		}

		System.out.println("Are you sure you want to cancel enrollment: " + targetEnrollment.getStudentName() + " in "
				+ targetEnrollment.getCourseName() + "? (y/n)");
		String confirm = scanner.nextLine();

		if (confirm.equalsIgnoreCase("y")) {
			if (enrollmentDAO.cancelEnrollment(enrollmentId)) {
				System.out.println("Enrollment canceled successfully!");
			} else {
				System.out.println("Failed to cancel enrollment.");
			}
		} else {
			System.out.println("Cancellation canceled.");
		}
	}
}
