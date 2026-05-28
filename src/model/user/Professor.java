package model.user;
import model.course.Course;

import java.util.*;
public class Professor extends User {
    private static int idMaker = 1;
    private List<Course> courses = new ArrayList<>();;
    public Professor(String name, String password) {
        super("PRO-",name, password , idMaker++);
    }
    @Override
    protected void showMenu() {
        System.out.println("===== PROFESSOR MENU =====");
        System.out.println("1. My Courses");
        System.out.println("2. Course Students");
        System.out.println("0. Logout");
        System.out.println("==========================");
    }

    public void getMyCourse(){
        int temp = 1;
        System.out.println("===== MY COURSES =====");
        for (Course course : this.courses){
            System.out.println((temp++) + ". " + course);
        }
        System.out.println("======================");
    }

    public void getCourseStudents(Course course){
        int temp = 1;
        System.out.println("===== STUDENTS IN "+ course.getTitle() +" =====");
        for (Student student : course.getStudents()){
            System.out.println((temp++) + ". " + student.getId() + " | " + student.getName());
        }
        System.out.println("======================");
    }

    public void addCourse(Course course){
        this.courses.add(course);
    }
}
