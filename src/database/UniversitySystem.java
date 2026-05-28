package database;

import model.course.Course;
import model.user.Admin;
import model.user.Professor;
import model.user.Student;
import java.util.*;

public class UniversitySystem {
    private final Admin admin = new Admin("admin" ,"admin");
    private List<Student> students = new ArrayList<>();
    private List<Professor> professors = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    //CR-Student
    public void create(Student student){
        this.students.add(student);
    }
    public void remove(Student student){
        this.students.remove(student);
    }
    //CR-Professor
    public void create(Professor professor){
        this.professors.add(professor);
    }
    public void remove(Professor professor){
        this.professors.remove(professor);
    }
    //CR-Course
    public void create(Course course){
        this.courses.add(course);
    }
    public void remove(Course course){
        this.courses.remove(course);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public Admin getAdmin() {
        return admin;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
