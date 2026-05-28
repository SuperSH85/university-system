package database;

import model.course.Course;
import model.user.User;
import java.util.*;

public class UniversitySystem {
    private List<User> users = new ArrayList<>();;
    private List<Course> courses = new ArrayList<>();;

    //CR-User
    protected void create(User user){
        this.users.add(user);
    }
    protected void remove(User user){
        this.users.remove(user);
    }

    //CR-Course
    protected void create(Course course){
        this.courses.add(course);
    }
    protected void remove(Course course){
        this.courses.remove(course);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
