package model.user;

import database.UniversitySystem;
import exception.InvalidInputException;
import exception.OperationCancelledException;
import model.course.Course;
import model.course.CourseTime;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Admin extends User{
    private static int idMaker = 1;
    // for creating NEW Admin (generates ID)
    public Admin(String name, String password) {
        super("A-" + (++idMaker), name, password);
    }

    // for loading FROM database (uses existing ID)
    public Admin(String id, String name, String password) {
        super(id, name, password);
    }
    @Override
    public void showMenu(Scanner scn){
        int tChoice = - 99;
        do{
            try {
                System.out.println("===== ADMIN MENU =====");
                System.out.println("1. Add New Course");
                System.out.println("2. Add New User");
                System.out.println("0. Logout");
                System.out.println("======================");
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
                Course course = null;
                exit = false;
                while (true){
                    try {
                        course = initCourse(scn);
                        break;
                    } catch (OperationCancelledException j) {
                        System.out.println(j.getMessage());
                        exit = true;
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (!exit){
                    this.addCourse(course);
                }
                break;
            case 2:
                User user = null;
                exit = false;
                while (true){
                    try {
                        user = initUser(scn);
                        break;
                    } catch (OperationCancelledException j) {
                        System.out.println(j.getMessage());
                        exit = true;
                        break;
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (!exit) this.addUser(user);
                break;
            case 0:
                contiueStatus = false;
                break;
        }
        return contiueStatus;
    }

    private  User initUser(Scanner scn) throws InvalidInputException , OperationCancelledException{
        System.out.println("Enter user type(1-3) (-1 for exit): ");
        System.out.println("1. Student (STU-)");
        System.out.println("2. Professor (PRO-)");
        System.out.println("3. Admin (A-)");
        int type = this.safeIntInput(scn);
        if (type == -1 ) throw new OperationCancelledException();
        if (type < 1 || type > 3) {
            throw new InvalidInputException("Invalid choice! Enter 1, 2, or 3.");
        }
        System.out.println("Enter name (-1 for exit): ");
        String name = scn.nextLine();
        if (name.equals("-1")) throw new OperationCancelledException();

        System.out.println("Enter password (-1 for exit): ");
        String password = scn.nextLine();
        if (password.equals("-1")) throw new OperationCancelledException();

        User newUser = switch (type) {
            case 1 -> new Student(name, password);
            case 2 -> new Professor(name, password);
            case 3 -> new Admin(name, password);
            default -> null;
        };
        System.out.println("User created successfully! ID: " + newUser.getId());
        return newUser;
    }

    private Course initCourse(Scanner scn) throws InvalidInputException  , OperationCancelledException{
        System.out.println("Enter course title (-1 for exit): ");
        String title = scn.nextLine();
        if (title.equals("-1")) throw new OperationCancelledException();

        System.out.println("Enter credits (-1 for exit): ");
        int credits = safeIntInput(scn);
        if (credits == -1) throw new OperationCancelledException();

        System.out.println("Enter capacity (-1 for exit): ");
        int capacity = safeIntInput(scn);
        if (capacity == -1) throw new OperationCancelledException();

        System.out.println("Enter day (e.g. MONDAY) (-1 for exit): ");
        DayOfWeek day;
        try {
            String temp = scn.nextLine().toUpperCase();
            if (temp.equals("-1")) throw new OperationCancelledException();
            day = DayOfWeek.valueOf(temp);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid day! Use e.g. MONDAY, TUESDAY...");
        }

        System.out.println("Enter start time (e.g. 10:00) (-1 for exit): ");
        LocalTime startTime;
        try {
            String temp = scn.nextLine();
            if (temp.equals("-1")) throw new OperationCancelledException();
            startTime = LocalTime.parse(temp);
        } catch (Exception e) {
            throw new InvalidInputException("Invalid time format! Use e.g. 10:00");
        }

        System.out.println("Enter end time (e.g. 12:00) (-1 for exit): ");
        LocalTime endTime;
        try {
            String temp = scn.nextLine();
            if (temp.equals("-1")) throw new OperationCancelledException();
            endTime = LocalTime.parse(temp);
        } catch (Exception e) {
            throw new InvalidInputException("Invalid time format! Use e.g. 12:00");
        }

        CourseTime schedule = new CourseTime(day, startTime, endTime);

        System.out.println("Select professor (-1 for exit):");
        List<User> users = UniversitySystem.getUsers();

        for (User u : users) {
            if (u instanceof Professor) {
                System.out.println(u.getId() + ": " + u.getName());
            }
        }

        Professor professor = null;
        String profChoice = scn.nextLine();
        if (profChoice.equals("-1")) throw new OperationCancelledException();
        for (User u : users) {
            if (u instanceof Professor && u.matches(profChoice)) {
                professor = (Professor) u;
            }
        }
        if (professor == null) {
            throw new InvalidInputException("Professor not found!");
        }

        return new Course(title, credits, capacity, schedule, professor);
    }

    public void addCourse(Course course){
        UniversitySystem.create(course);
    }

    public void addUser(User user){
        UniversitySystem.create(user);
    }

    @Override
    public String getRole() { return "ADMIN"; }
}
