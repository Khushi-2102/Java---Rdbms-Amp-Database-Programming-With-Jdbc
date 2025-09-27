package service;

import DB.CourseDAO;
import model.Course;
import java.util.List;
import java.util.Scanner;

public class CourseService {
	private CourseDAO courseDAO;
	private Scanner scanner;

	public CourseService() {
		this.courseDAO = new CourseDAO();
		this.scanner = new Scanner(System.in);
	}

	public void addCourse() {
		System.out.println("\n=== Add New Course ===");

		System.out.print("Enter course name: ");
		String name = scanner.nextLine();

		System.out.print("Enter fees: ");
		double fees = scanner.nextDouble();

		System.out.print("Enter duration (months): ");
		int duration = scanner.nextInt();
		scanner.nextLine();

		Course course = new Course(name, fees, duration);

		if (courseDAO.addCourse(course)) {
			System.out.println("Course added successfully!");
		} else {
			System.out.println("Failed to add course.");
		}
	}

	public void viewAllCourses() {
		System.out.println("\n=== All Courses ===");
		List<Course> courses = courseDAO.getAllCourses();

		if (courses.isEmpty()) {
			System.out.println("No courses found.");
		} else {
			courses.forEach(System.out::println);
		}
	}

	public void updateCourse() {
		System.out.println("\n=== Update Course ===");
		System.out.print("Enter course ID to update: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		Course course = courseDAO.getCourseById(id);
		if (course == null) {
			System.out.println("Course not found.");
			return;
		}

		System.out.println("Current details: " + course);

		System.out.print("Enter new course name (current: " + course.getCourseName() + "): ");
		String name = scanner.nextLine();
		if (!name.isEmpty())
			course.setCourseName(name);

		System.out.print("Enter new fees (current: " + course.getFees() + "): ");
		String feesInput = scanner.nextLine();
		if (!feesInput.isEmpty())
			course.setFees(Double.parseDouble(feesInput));

		System.out.print("Enter new duration (current: " + course.getDurationMonths() + "): ");
		String durationInput = scanner.nextLine();
		if (!durationInput.isEmpty())
			course.setDurationMonths(Integer.parseInt(durationInput));

		if (courseDAO.updateCourse(course)) {
			System.out.println("Course updated successfully!");
		} else {
			System.out.println("Failed to update course.");
		}
	}

	public void deleteCourse() {
		System.out.println("\n=== Delete Course ===");
		System.out.print("Enter course ID to delete: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		Course course = courseDAO.getCourseById(id);
		if (course == null) {
			System.out.println("Course not found.");
			return;
		}

		System.out.println("Are you sure you want to delete course: " + course.getCourseName() + "? (y/n)");
		String confirm = scanner.nextLine();

		if (confirm.equalsIgnoreCase("y")) {
			if (courseDAO.deleteCourse(id)) {
				System.out.println("Course deleted successfully!");
			} else {
				System.out.println("Failed to delete course.");
			}
		} else {
			System.out.println("Deletion canceled.");
		}
	}
}
