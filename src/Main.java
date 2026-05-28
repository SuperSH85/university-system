import database.UniversitySystem;
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
    public static void main(String[] args) {
        var scn = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║       UNIVERSITY ENROLLMENT SYSTEM      ║");
            System.out.println("║          Welcome! Please Login          ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println();
//            do{
//                System.out.println("1. Login");
//                System.out.println("0. Logout");
//                try {
//                    int welcome = scn.nextInt();
//                    if (welcome != 0 || welcome != 1){
//                        throw
//                    }
//                }catch (){
//
//                }
//
//
//            }while (welcome == 1);
            String name ;
            String password;
            do {
                System.out.println("Enter your Name: ");
                name = scn.nextLine();
                System.out.println("Enter your Password: ");
                password = scn.nextLine();
            }while (!isUserExist(name , password));

            User user = findUser(name , password);

            int choice = user.showMenu(scn);

        }
    }

    private static boolean isUserExist(String name , String password){
        boolean flag = false;
        for (User user : UniversitySystem.getUsers()){
            if (user.getName().equals(name) && user.getPassword().equals(password)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    private static User findUser(String name , String password){
        User user = null;
        boolean flag = false;
        for (User tempUser : UniversitySystem.getUsers()){
            if (tempUser.getName().equals(name) && tempUser.getPassword().equals(password)){
                user = tempUser;
                flag = true;
                break;
            }
        }
        //if user == null throw exception
        return user;
    }

    private static void handleMenu(Admin admin , int choice , Scanner scn){

    }
    private static void handleMenu(Professor professor , int choice){

    }
    private static void handleMenu(Student student , int choice){

    }

    private static Course initCourse(Scanner scn){
        System.out.println("Enter course title: ");
        String title = scn.nextLine();

        System.out.println("Enter credits: ");
        int credits = scn.nextInt();

        System.out.println("Enter capacity: ");
        int capacity = scn.nextInt();

        System.out.println("Enter day (e.g. MONDAY): ");
        DayOfWeek day = DayOfWeek.valueOf(scn.next().toUpperCase());

        System.out.println("Enter start time (e.g. 10:00): ");
        LocalTime startTime = LocalTime.parse(scn.next());

        System.out.println("Enter end time (e.g. 12:00): ");
        LocalTime endTime = LocalTime.parse(scn.next());

        CourseTime schedule = new CourseTime(day, startTime, endTime);

        boolean flag = false;
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
}
