package database;

import model.course.Course;
import model.user.Admin;
import model.user.User;

import java.util.*;

public class UniversitySystem {
    private static final Admin admin = new Admin("admin" ,"admin");
    private static List<User> users = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    static {
        users.add(admin);
    }
    private UniversitySystem() {}

    //CR-User
    public static void create(User user){
        users.add(user);
    }
    public static void remove(User user){
        users.remove(user);
    }
    //CR-Course
    public static void create(Course course){
        courses.add(course);
    }
    public static void remove(Course course){
        courses.remove(course);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static List<Course> getCourses() {
        return courses;
    }
}
