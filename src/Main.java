import database.UniversitySystem;
import exception.CourseFullException;
import exception.CourseNotFoundException;
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
    static void main(String[] args) {
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
            User user;
            while (true){
                try {
                    user = authentication(scn);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            while (flag) {
                int choice;
                while (true){
                    try {
                        choice = user.showMenu(scn);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
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
        if (tChoice != 0 && tChoice != 1){
            throw new InvalidInputException("Invalid input! Please enter 0 or 1");
        }
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
            throw new UserNotFoundException("wrong name or password at login");
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
        for (User tempUser : UniversitySystem.getUsers()){
            if (tempUser.getName().equals(name) && tempUser.getPassword().equals(password)){
                user = tempUser;
                break;
            }
        }
        return user;
    }

    private static void handleMenu(Admin admin , int choice , Scanner scn){
        switch (choice) {
            case 1:
                Course course;
                while (true){
                    try {
                        course = initCourse(scn);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                admin.addCourse(course);
                break;
            case 2:
                User user;
                while (true){
                    try {
                        user = initUser(scn);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
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
                Course course;
                while (true){
                    try {
                        course = askForCourse(professor, scn);
                        break;
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                professor.getCourseStudents(course);
                break;
            case 0:
                flag = false;
                break;
        }
    }
    private static void handleMenu(Student student , int choice , Scanner scn){
        Course course;
        switch (choice) {
            case 1:
                student.viewAllCourses();
                break;
            case 2:
                while (true) {
                    try {
                        course = askForCourse(student, scn);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                try {
                    student.enrollCourse(course);
                    System.out.println("✅ Enrolled successfully!");
                } catch (CourseFullException e) {
                    System.out.println("❌ " + e.getMessage());
                    try{
                        course.addToWaitlist(student);
                        System.out.println("✅ Added to waitlist!");
                    }catch (Exception a){
                        System.out.println(a.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("❌ " + e.getMessage());
                }
                break;
            case 3:
                while (true) {
                    try {
                        course = askForCourse(student, scn);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                try {
                    student.dropCourse(course);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                student.getMyCourse();
                break;
            case 0:
                flag = false;
                break;
        }
    }

    private static Course initCourse(Scanner scn) throws InvalidInputException {
        System.out.println("Enter course title: ");
        String title = scn.nextLine();

        System.out.println("Enter credits: ");
        int credits = safeIntInput(scn);

        System.out.println("Enter capacity: ");
        int capacity = safeIntInput(scn);

        System.out.println("Enter day (e.g. MONDAY): ");
        DayOfWeek day;
        try {
            day = DayOfWeek.valueOf(scn.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid day! Use e.g. MONDAY, TUESDAY...");
        }

        System.out.println("Enter start time (e.g. 10:00): ");
        LocalTime startTime;
        try {
            startTime = LocalTime.parse(scn.nextLine());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid time format! Use e.g. 10:00");
        }

        System.out.println("Enter end time (e.g. 12:00): ");
        LocalTime endTime;
        try {
            endTime = LocalTime.parse(scn.nextLine());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid time format! Use e.g. 12:00");
        }

        CourseTime schedule = new CourseTime(day, startTime, endTime);

        System.out.println("Select professor:");
        List<User> users = UniversitySystem.getUsers();
        for (User u : users) {
            if (u instanceof Professor) {
                System.out.println(u.getId() + ": " + u.getName());
            }
        }

        Professor professor = null;
        String profChoice = scn.nextLine();
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

    private static User initUser(Scanner scn) throws InvalidInputException{
        System.out.println("Enter user type: ");
        System.out.println("1. Student (STU-)");
        System.out.println("2. Professor (PRO-)");
        System.out.println("3. Admin (A-)");
        int type = safeIntInput(scn);
        if (type < 1 || type > 3) {
            throw new InvalidInputException("Invalid choice! Enter 1, 2, or 3.");
        }
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
        System.out.println("User created successfully! ID: " + newUser.getId());
        return newUser;
    }

    private static Course askForCourse(Professor professor , Scanner scn) throws CourseNotFoundException{
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
        if (course == null) {
            throw new CourseNotFoundException("❌ Course not found!");
        }
        return course;
    }

    private static Course askForCourse(Student Student , Scanner scn) throws CourseNotFoundException{
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
        if (course == null) {
            throw new CourseNotFoundException("❌ Course not found!");
        }
        return course;
    }

    private static int safeIntInput(Scanner scn) throws InvalidInputException {
        String input = scn.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input! Please enter a number.");
        }
    }
}
