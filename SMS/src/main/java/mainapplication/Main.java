package mainapplication;
import service.StudentService;
import service.CourseService;
import service.EnrollmentService;
import java.util.Scanner;
public class Main {
	private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        initializeServices();
        displayMainMenu();
    }
    
    private static void initializeServices() {
        studentService = new StudentService();
        courseService = new CourseService();
        enrollmentService = new EnrollmentService();
        scanner = new Scanner(System.in);
    }
    
    private static void displayMainMenu() {
        while (true) {
            System.out.println("\n=== Student Course Management System ===");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Enrollment Management");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    studentManagementMenu();
                    break;
                case 2:
                    courseManagementMenu();
                    break;
                case 3:
                    enrollmentManagementMenu();
                    break;
                case 4:
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void studentManagementMenu() {
        while (true) {
            System.out.println("\n=== Student Management ===");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    studentService.addStudent();
                    break;
                case 2:
                    studentService.viewAllStudents();
                    break;
                case 3:
                    studentService.searchStudent();
                    break;
                case 4:
                    studentService.updateStudent();
                    break;
                case 5:
                    studentService.deleteStudent();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    private static void courseManagementMenu() {
        while (true) {
            System.out.println("\n=== Course Management ===");
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Update Course");
            System.out.println("4. Delete Course");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    courseService.addCourse();
                    break;
                case 2:
                    courseService.viewAllCourses();
                    break;
                case 3:
                    courseService.updateCourse();
                    break;
                case 4:
                    courseService.deleteCourse();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    private static void enrollmentManagementMenu() {
        while (true) {
            System.out.println("\n=== Enrollment Management ===");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. View All Enrollments");
            System.out.println("3. View Student Enrollments");
            System.out.println("4. Cancel Enrollment");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    enrollmentService.enrollStudent();
                    break;
                case 2:
                    enrollmentService.viewAllEnrollments();
                    break;
                case 3:
                    enrollmentService.viewStudentEnrollments();
                    break;
                case 4:
                    enrollmentService.cancelEnrollment();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
