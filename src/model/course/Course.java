package model.course;
import model.Searchable;
import model.user.Professor;
import model.user.Student;
import java.util.*;

public class Course implements Searchable {

    private String courseID;
    private String title;
    private int credits;
    private int capacity;
    private CourseTime schedule;
    private List<Student> students = new ArrayList<>();
    private Professor professor;
    private static int idMaker = 0;

    public Course(String title ,
                  int credits ,
                  int capacity ,
                  CourseTime schedule ,
                  Professor professor){
        this.courseID = "CRS-" + (++idMaker);
        this.capacity = capacity;
        this.credits = credits;
        this.schedule = schedule;
        this.professor = professor;
        professor.addCourse(this);
    }
    @Override
    public boolean matches(String keyword) {
        return this.courseID.equals(keyword);
    }

    public String getCourseID() {
        return courseID;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public CourseTime getSchedule() {
        return schedule;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public void removeStudent(Student student){
        this.students.remove(student);
    }
    @Override
    public String toString() {
        return courseID + " | " + title + " | " + credits + " credits | " + schedule;
    }
}
