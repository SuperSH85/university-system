import database.UserDAO;
import exception.InvalidInputException;
import exception.UserNotFoundException;
import model.user.User;

import java.sql.SQLException;
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
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByNameAndPassword(name, password);
            if (user == null) throw new UserNotFoundException("Wrong name or password!");
            return user;
        } catch (SQLException e) {
            throw new UserNotFoundException("Database error: " + e.getMessage());
        }
    }
}
