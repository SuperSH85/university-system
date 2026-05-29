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
    public int showMenu(Scanner scn) {
        System.out.println("===== STUDENT MENU =====");
        System.out.println("1. View All Courses");
        System.out.println("2. Enroll Course");
        System.out.println("3. Drop Course");
        System.out.println("4. My Courses");
        System.out.println("0. Logout");
        System.out.println("========================");
        int choice = scn.nextInt();
        //throw invalid  if choice not between the options or not int
        return choice;
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
