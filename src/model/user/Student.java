package model.user;
import database.CourseDAO;
import database.EnrollmentDAO;
import database.UserDAO;
import database.WaitlistDAO;
import exception.*;
import model.course.Course;
import java.sql.SQLException;
import java.util.*;

public class Student extends User {
    private static int idMaker = 1;
    // for creating NEW student (generates ID)
    public Student(String name, String password) {
        super("STU-" + (++idMaker), name, password);
    }
    // for loading FROM database (uses existing ID)
    public Student(String id, String name, String password) {
        super(id, name, password);
    }
    @Override
    public void showMenu(Scanner scn){
        int tChoice = -99;
        do{
            try{
                System.out.println("===== STUDENT MENU =====");
                System.out.println("1. View All Courses");
                System.out.println("2. Enroll Course");
                System.out.println("3. Drop Course");
                System.out.println("4. My Courses");
                System.out.println("0. Logout");
                System.out.println("========================");
                tChoice = safeIntInput(scn);
                if (tChoice != 0 && tChoice != 1 && tChoice != 2 && tChoice != 3 && tChoice != 4) {
                    throw new InvalidInputException("Invalid input! Please enter (0 , 1 , 2 , 3 , 4)");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }while (handleMenu(tChoice , scn));
    }

    @Override
    protected boolean handleMenu(int choice, Scanner scn) {
        Course course = null;
        boolean contiueStatus = true;
        boolean exit;
        switch (choice) {
            case 1:
                try {
                    this.viewAllCourses();
                } catch (OperationCancelledException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                exit = false;
                while (true) {
                    try {
                        course = askForCourse(scn);
                        break;
                    }catch (OperationCancelledException j) {
                        System.out.println(j.getMessage());
                        exit = true;
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (exit) break;
                try {
                    this.enrollCourse(course);
                    System.out.println("✅ Enrolled successfully!");
                }  catch (CourseFullException e) {
                    System.out.println("❌ " + e.getMessage());
                    try {
                        WaitlistDAO waitlistDAO = new WaitlistDAO();
                        waitlistDAO.add(this.getId(), course.getCourseID());
                        System.out.println("✅ Added to waitlist!");
                    } catch (SQLException a) {
                        System.out.println("❌ " + a.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("❌ " + e.getMessage());
                }
                break;
            case 3:
                exit = false;
                while (true) {
                    try {
                        course = askForCourse(scn);
                        break;
                    } catch (OperationCancelledException j) {
                        System.out.println(j.getMessage());
                        exit = true;
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (exit) break;
                try {
                    this.dropCourse(course);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                this.getMyCourse();
                break;
            case 0:
                contiueStatus = false;
                break;
        }
        return contiueStatus;
    }

    private static Course askForCourse(Scanner scn) throws CourseNotFoundException, OperationCancelledException {
        Course course = null;
        System.out.println("Select course (-1 for exit):");
        List<Course> courses;
        try {
            CourseDAO courseDAO = new CourseDAO();
            courses = courseDAO.findAll();
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            throw new OperationCancelledException();
        }
        if (courses == null || courses.isEmpty()) {
            System.out.println("No courses available!");
            throw new OperationCancelledException();
        }
        for (Course c : courses) {
            System.out.println(c.getCourseID() + ": " + c.getTitle());
        }
        String courseChoice = scn.nextLine();
        if (courseChoice.equals("-1")) throw new OperationCancelledException();
        for (Course c : courses) {
            if (c.matches(courseChoice)) {
                course = c;
            }
        }
        if (course == null) {
            throw new CourseNotFoundException("❌ Course not found!");
        }
        return course;
    }

    public void viewAllCourses() throws OperationCancelledException {
        System.out.println("===== ALL COURSES =====");
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses ;
        try {
            courses = courseDAO.findAll();
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            throw new OperationCancelledException();
        }
        for (Course course : courses){
            System.out.println(course);
        }
        System.out.println("======================");
    }

    public void enrollCourse(Course course) throws CourseFullException, CreditLimitExceededException, DuplicateCourseException, ScheduleConflictException {
        try {
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

            if (enrollmentDAO.isEnrolled(this.getId(), course.getCourseID())) {
                throw new DuplicateCourseException("You are already enrolled in: " + course.getTitle());
            }

            // check schedule conflict against student's current courses
            List<Course> myCourses = enrollmentDAO.findCoursesByStudent(this.getId());
            for (Course c : myCourses) {
                if (c.getSchedule().overlaps(course.getSchedule())) {
                    throw new ScheduleConflictException("Schedule conflict with: " + c.getTitle());
                }
            }

            int totalCredits = enrollmentDAO.getTotalCredits(this.getId());
            if (totalCredits + course.getCredits() > 20) {
                throw new CreditLimitExceededException("Credit limit exceeded! Max 20 credits. Current: " + totalCredits);
            }

            if (!enrollmentDAO.hasCapacity(course.getCourseID())) {
                throw new CourseFullException("Course is full: " + course.getTitle());
            }

            enrollmentDAO.enroll(this.getId(), course.getCourseID());

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    public void dropCourse(Course course) throws CourseNotFoundException {
        try {
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
            WaitlistDAO waitlistDAO = new WaitlistDAO();

            if (!enrollmentDAO.isEnrolled(this.getId(), course.getCourseID())) {
                throw new CourseNotFoundException("You are not enrolled in: " + course.getTitle());
            }

            enrollmentDAO.drop(this.getId(), course.getCourseID());

            // auto-promote from waitlist
            String nextStudentId = waitlistDAO.getFirst(course.getCourseID());
            if (nextStudentId != null) {
                UserDAO userDAO = new UserDAO();
                Student nextStudent = (Student) userDAO.findById(nextStudentId);
                try {
                    nextStudent.enrollCourse(course);
                    waitlistDAO.remove(nextStudentId, course.getCourseID());
                    System.out.println("✅ " + nextStudent.getName() + " enrolled from waitlist!");
                } catch (Exception e) {
                    System.out.println("⚠️ " + nextStudent.getName() + " could not be enrolled: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    public void getMyCourse() {
        int temp = 1;
        int totalCredits = 0;
        System.out.println("===== MY COURSES =====");
        try {
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
            List<Course> courses = enrollmentDAO.findCoursesByStudent(this.getId());
            for (Course course : courses) {
                System.out.println((temp++) + ". " + course);
                totalCredits += course.getCredits();
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
        System.out.println("Total Credits: " + totalCredits);
        System.out.println("======================");
    }

    @Override
    public String getRole() { return "STUDENT"; }
}