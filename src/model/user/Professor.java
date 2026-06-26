package model.user;
import database.CourseDAO;
import exception.CourseNotFoundException;
import exception.InvalidInputException;
import exception.OperationCancelledException;
import model.course.Course;
import java.sql.SQLException;
import java.util.*;
public class Professor extends User {
    private static int idMaker = 1;
    // for creating NEW professor (generates ID)
    public Professor(String name, String password) {
        super("PRO-" + (++idMaker), name, password);
    }

    // for loading FROM database (uses existing ID)
    public Professor(String id, String name, String password) {
        super(id, name, password);
    }
    @Override
    public void showMenu(Scanner scn){
        int tChoice = - 99;
        do{
            try{
                System.out.println("===== PROFESSOR MENU =====");
                System.out.println("1. My Courses");
                System.out.println("2. Course Students");
                System.out.println("0. Logout");
                System.out.println("==========================");
                tChoice = safeIntInput(scn);
                if (tChoice != 0 && tChoice != 1 && tChoice != 2) {
                    throw new InvalidInputException("Invalid input! Please enter (0, 1, 2)");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }while (handleMenu(tChoice , scn));
    }

    @Override
    protected boolean handleMenu(int choice, Scanner scn) {
        boolean contiueStatus = true;
        boolean exit;
        switch (choice) {
            case 1:
                this.getMyCourse();
                break;
            case 2:
                Course course = null;
                exit = false;
                while (true){
                    try {
                        course = askForCourse(scn);
                        break;
                    }catch (OperationCancelledException j) {
                        System.out.println(j.getMessage());
                        exit = true;
                        break;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                if (!exit) this.getCourseStudents(course);
                break;
            case 0:
                contiueStatus = false;
                break;
        }
        return contiueStatus;
    }

    public List<Course> getCourses() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            return courseDAO.findByProfessor(this.getId());
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void getMyCourse(){
        int temp = 1;
        System.out.println("===== MY COURSES =====");
        for (Course course : getCourses()){
            System.out.println((temp++) + ". " + course);
        }
        System.out.println("======================");
    }

    private Course askForCourse(Scanner scn) throws CourseNotFoundException , OperationCancelledException {
        Course course = null;
        System.out.println("Select course (-1 for exit):");
        List<Course> courses = this.getCourses();
        if (courses == null || courses.isEmpty()) {
            System.out.println("You have no courses!");
            throw new OperationCancelledException();
        }
        for (Course c : courses) {
            System.out.println(c.getCourseID() + ": " + c.getTitle());
        }
        String profChoice = scn.nextLine();
        if (profChoice.equals("-1")) throw new OperationCancelledException();
        for (Course c : courses) {
            if (c.matches(profChoice)) {
                course = c;
            }
        }
        if (course == null) {
            throw new CourseNotFoundException("❌ Course not found!");
        }
        return course;
    }

    public void getCourseStudents(Course course){
        int temp = 1;
        System.out.println("===== STUDENTS IN "+ course.getTitle() +" =====");
        for (Student student : course.getStudents()){
            System.out.println((temp++) + ". " + student.getId() + " | " + student.getName());
        }
        System.out.println("======================");
    }

    @Override
    public String getRole() { return "PROFESSOR"; }
}
