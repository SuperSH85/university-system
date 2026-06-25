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
    static void main(String[] args) {
        var scn = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════╗");
        System.out.println("║       UNIVERSITY ENROLLMENT SYSTEM      ║");
        System.out.println("║          Welcome! Please Login          ║");
        System.out.println("╚═════════════════════════════════════════╝");
        System.out.println();

        while(true){
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
            user.showMenu(scn);
            System.out.println();
            System.out.println();
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

    private static User authentication(Scanner scn) throws UserNotFoundException {
        System.out.println("Enter your Name: ");
        String name = scn.nextLine();
        System.out.println("Enter your Password: ");
        String password = scn.nextLine();
        for (User user : UniversitySystem.getUsers()) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new UserNotFoundException("Wrong name or password!");
    }
}
