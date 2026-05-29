import database.UniversitySystem;
import exception.InvalidInputException;
import exception.UserNotFoundException;
import model.course.Course;
import model.course.CourseTime;
import model.user.Admin;
import model.user.Professor;
import model.user.Student;
import model.user.User;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static boolean flag = true;
    public static void main(String[] args) {
        var scn = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════╗");
        System.out.println("║       UNIVERSITY ENROLLMENT SYSTEM      ║");
        System.out.println("║          Welcome! Please Login          ║");
        System.out.println("╚═════════════════════════════════════════╝");
        System.out.println();

        while(true){
            flag = true;
            int welcome;
            while (true){
                try {
                    welcome = loginExitChoice(scn);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (welcome == 0){
                break;
            }
            while (flag) {
                User user;
                while (true){
                    try {
                        user = authentication(scn);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                int choice = user.showMenu(scn);
                if (user instanceof Admin) {
                    handleMenu((Admin) user, choice, scn);
                } else if (user instanceof Professor) {
                    handleMenu((Professor) user, choice, scn);
                } else if (user instanceof Student) {
                    handleMenu((Student) user, choice, scn);
                }
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("Goodbye");
    }

    private static int loginExitChoice(Scanner scn) throws InvalidInputException {
        System.out.println("1. Login");
        System.out.println("0. Exit");
        String choice = scn.nextLine();
        int tChoice;
        try {
            tChoice = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input! Please enter a number.");
        }
        if (tChoice != 0 || tChoice != 1){
            throw new InvalidInputException("Invalid input! Please enter 0 or 1");
        }
        scn.nextLine();
        return tChoice;
    }

    private static User authentication(Scanner scn) throws UserNotFoundException{
        String name;
        String password;
        System.out.println("Enter your Name: ");
        name = scn.nextLine();
        System.out.println("Enter your Password: ");
        password = scn.nextLine();
        if(!isUserExist(name, password)){
            throw new UserNotFoundException("wrong name or password at login")
        }
        return findUser(name, password);
    }

    private static boolean isUserExist(String name , String password){
        boolean tFlag = false;
        for (User user : UniversitySystem.getUsers()){
            if (user.getName().equals(name) && user.getPassword().equals(password)){
                tFlag = true;
                break;
            }
        }
        return tFlag;
    }

    private static User findUser(String name , String password){
        User user = null;
        boolean tFlag = false;
        for (User tempUser : UniversitySystem.getUsers()){
            if (tempUser.getName().equals(name) && tempUser.getPassword().equals(password)){
                user = tempUser;
                tFlag = true;
                break;
            }
        }
        //if user == null throw exception
        return user;
    }

    private static void handleMenu(Admin admin , int choice , Scanner scn){
        switch (choice) {
            case 1:
                Course course = initCourse(scn);
                admin.addCourse(course);
                break;
            case 2:
                User user = initUser(scn);
                admin.addUser(user);
                break;
            case 0:
                flag = false;
                break;
        }

    }
    private static void handleMenu(Professor professor , int choice ,Scanner scn){
        switch (choice) {
            case 1:
                professor.getMyCourse();
                break;
            case 2:
                Course course = askForCourse(professor, scn);
                professor.getCourseStudents(course);
                break;
            case 0:
                flag = false;
                break;
        }
    }
    private static void handleMenu(Student student , int choice , Scanner scn){
        Course course = null;
        switch (choice) {
            case 1:
                student.viewAllCourses();
                break;
            case 2:
                course = askForCourse(student, scn);
                student.enrollCourse(course);
                break;
            case 3:
                course = askForCourse(student, scn);
                student.dropCourse(course);
                break;
            case 4:
                student.getMyCourse();
                break;
            case 0:
                flag = false;
                break;
        }
    }

    private static Course initCourse(Scanner scn){
        System.out.println("Enter course title: ");
        String title = scn.nextLine();

        System.out.println("Enter credits: ");
        int credits = scn.nextInt();
        scn.nextLine();

        System.out.println("Enter capacity: ");
        int capacity = scn.nextInt();
        scn.nextLine();

        System.out.println("Enter day (e.g. MONDAY): ");
        DayOfWeek day = DayOfWeek.valueOf(scn.next().toUpperCase());

        System.out.println("Enter start time (e.g. 10:00): ");
        LocalTime startTime = LocalTime.parse(scn.next());

        System.out.println("Enter end time (e.g. 12:00): ");
        LocalTime endTime = LocalTime.parse(scn.next());
        scn.nextLine();
        CourseTime schedule = new CourseTime(day, startTime, endTime);

        Professor professor = null;

        System.out.println("Select professor:");
        List<User> users = UniversitySystem.getUsers();
        for (User u : users) {
            if (u instanceof Professor) {
                System.out.println(u.getId() + ": " + u.getName());
            }
        }
        String profChoice = scn.nextLine();
        for (User u : users) {
            if (u instanceof Professor && u.matches(profChoice)) {
                professor = (Professor) u;
            }
        }
        Course course = new Course(title, credits, capacity, schedule, professor);
        return course;
    }

    private static User initUser(Scanner scn){
        System.out.println("Enter user type: ");
        System.out.println("1. Student (STU-)");
        System.out.println("2. Professor (PRO-)");
        System.out.println("3. Admin (A-)");
        int type = scn.nextInt();
        scn.nextLine();

        System.out.println("Enter name: ");
        String name = scn.nextLine();

        System.out.println("Enter password: ");
        String password = scn.nextLine();

        User newUser = switch (type) {
            case 1 -> new Student(name, password);
            case 2 -> new Professor(name, password);
            case 3 -> new Admin(name, password);
            default -> null;
        };

        if (newUser != null) {
            UniversitySystem.create(newUser);
            System.out.println("User created successfully! ID: " + newUser.getId());
        }
        return newUser;
    }

    private static Course askForCourse(Professor professor , Scanner scn){
        Course course = null;
        System.out.println("Select course:");
        List<Course> courses = professor.getCourses();
        for (Course c : courses) {
            System.out.println(c.getCourseID() + ": " + c.getTitle());
        }
        String profChoice = scn.nextLine();
        for (Course c : courses) {
            if (c.matches(profChoice)) {
                course = c;
            }
        }
        return course;
    }

    private static Course askForCourse(Student Student , Scanner scn){
        Course course = null;
        System.out.println("Select course:");
        List<Course> courses = UniversitySystem.getCourses();
        for (Course c : courses) {
            System.out.println(c.getCourseID() + ": " + c.getTitle());
        }
        String courseChoice = scn.nextLine();
        for (Course c : courses) {
            if (c.matches(courseChoice)) {
                course = c;
            }
        }
        return course;
    }
}
