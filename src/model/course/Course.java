package model.course;
import model.Searchable;
import model.user.Professor;

public class Course implements Searchable {

    private String courseID;
    private String title;
    private int credits;
    private int capacity;
    private CourseTime schedule;
    private Professor professor;
    private static int idMaker = 0;

    public Course(String title ,
                  int credits ,
                  int capacity ,
                  CourseTime schedule ,
                  Professor professor){
        this.title = title;
        this.courseID = "CRS-" + (++idMaker);
        this.capacity = capacity;
        this.credits = credits;
        this.schedule = schedule;
        this.professor = professor;
    }
    //for DB
    public Course(String courseId, String title, int credits,
                  int capacity, CourseTime schedule, Professor professor) {
        this.courseID = courseId;
        this.title = title;
        this.credits = credits;
        this.capacity = capacity;
        this.schedule = schedule;
        this.professor = professor;
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


    public Professor getProfessor() {
        return professor;
    }

    @Override
    public String toString() {
        return courseID + " | " + title + " | " + credits + " credits | " + schedule;
    }
}
