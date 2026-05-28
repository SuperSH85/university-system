package database;

import model.course.Course;
import model.user.Admin;
import model.user.Professor;
import model.user.Student;
import model.user.User;

import java.util.*;

public class UniversitySystem {
    private static final Admin admin = new Admin("admin" ,"admin");
    private static List<Student> students = new ArrayList<>();
    private static List<Professor> professors = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();

    private UniversitySystem() {}
    //CR-Student
    public static void create(Student student){
        students.add(student);
    }
    public static void remove(Student student){
        students.remove(student);
    }
    //CR-Professor
    public static void create(Professor professor){
        professors.add(professor);
    }
    public static void remove(Professor professor){
        professors.remove(professor);
    }
    //CR-Course
    public static void create(Course course){
        courses.add(course);
    }
    public static void remove(Course course){
        courses.remove(course);
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static List<Professor> getProfessors() {
        return professors;
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        all.add(admin);
        all.addAll(students);
        all.addAll(professors);
        return all;
    }
}
