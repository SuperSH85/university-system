package model.user;
import database.UniversitySystem;
import exception.*;
import model.course.Course;
import java.util.*;

public class Student extends User {
    private List<Course > courses = new ArrayList<>();
    private static int idMaker = 1;
    public Student(String name , String password){
        super("STU-",name, password , idMaker++);
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
                this.viewAllCourses();
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
                } catch (CourseFullException e) {
                    System.out.println("❌ " + e.getMessage());
                    try{
                        course.addToWaitlist(this);
                        System.out.println("✅ Added to waitlist!");
                    }catch (Exception a){
                        System.out.println(a.getMessage());
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

    private static Course askForCourse(Scanner scn) throws CourseNotFoundException  , OperationCancelledException{
        Course course = null;
        System.out.println("Select course (-1 for exit):");
        List<Course> courses = UniversitySystem.getCourses();
        if (courses == null || courses.isEmpty()) {
            System.out.println("You have no courses!");
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

    public List<Course> getCourses(){
        return this.courses;
    }

    public void viewAllCourses(){
        System.out.println("===== ALL COURSES =====");
        for (Course course : UniversitySystem.getCourses()){
            System.out.println(course);
        }
        System.out.println("======================");
    }

    public void enrollCourse(Course course) throws CourseFullException , CreditLimitExceededException , DuplicateCourseException , ScheduleConflictException {
        int totalCredits = 0;
        for (Course c : this.courses){
            totalCredits += c.getCredits();
            if (c.getCourseID().equals(course.getCourseID())){
                throw new DuplicateCourseException("You are already enrolled in: " + course.getTitle());
            }
            if(c.getSchedule().overlaps(course.getSchedule())){
                throw new ScheduleConflictException("Schedule conflict with: " + c.getTitle());
            }
        }
        if (totalCredits + course.getCredits() > 20){
            throw new CreditLimitExceededException("Credit limit exceeded! Max 20 credits allowed. Current: " + totalCredits);
        }

        if (course.getStudents().size() >= course.getCapacity()){
            throw new CourseFullException("Course is full: " + course.getTitle());
        }

        this.courses.add(course);
        course.addStudent(this);
    }

    public void dropCourse(Course course) throws CourseNotFoundException{
        if (!this.courses.contains(course)){
            throw new CourseNotFoundException("You are not enrolled in: " + course.getTitle());
        }
        this.courses.remove(course);
        course.removeStudent(this);
        Student waitListStudent = course.removeFromWaitList();
        if (waitListStudent != null) {
            try {
                waitListStudent.enrollCourse(course);
                System.out.println("✅ " + waitListStudent.getName() + " enrolled from waitlist!");
            } catch (Exception e) {
                System.out.println("⚠️ " + waitListStudent.getName() + " could not be enrolled: " + e.getMessage());
            }
        }
    }

    public void getMyCourse(){
        int temp = 1;
        int totalCredits = 0;
        System.out.println("===== MY COURSES =====");
        for (Course course : this.courses){
            System.out.println((temp++) + ". " + course);
            totalCredits += course.getCredits();
        }
        System.out.println("Total Credits: " + totalCredits);
        System.out.println("======================");
    }
}
