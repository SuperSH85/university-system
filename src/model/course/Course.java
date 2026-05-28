package model.course;
import model.Searchable;
import model.user.Professor;
import model.user.Student;
import java.util.*;

public class Course implements Searchable {

    private int courseID;
    private String title;
    private int credits;
    private int capacity;
    private CourseTime schedule;
    private ArrayList<Student> students;
    private Professor professor;
    static int idMaker = 0;

    public Course(String title ,
                  int credits ,
                  int capacity ,
                  CourseTime schedule ,
                  Professor professor){
        this.courseID = ++idMaker;
        this.capacity = capacity;
        this.credits = credits;
        this.schedule = schedule;
        this.professor = professor;
    }

    @Override
    public boolean matches(String keyword) {
        return false;
    }
}
