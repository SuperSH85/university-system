package model.user;

import database.UniversitySystem;
import exception.InvalidInputException;
import model.course.Course;

import java.util.Scanner;

public class Admin extends User{
    private static int idMaker = 1;
    public Admin(String name, String password) {
        super("A-",name, password , idMaker++);
    }
    @Override
    public int showMenu(Scanner scn) throws InvalidInputException {
        System.out.println("===== ADMIN MENU =====");
        System.out.println("1. Add New Course");
        System.out.println("2. Add New User");
        System.out.println("0. Logout");
        System.out.println("======================");
        String choice = scn.nextLine();
        int tChoice;
        try {
            tChoice = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input! Please enter a number.");
        }
        if (tChoice != 0 || tChoice != 1 || tChoice != 2){
            throw new InvalidInputException("Invalid input! Please enter (0 , 1 , 2)");
        }
        scn.nextLine();
        return tChoice;
    }

    public void addCourse(Course course){
        UniversitySystem.create(course);
    }

    public void addUser(User user){
        UniversitySystem.create(user);
    }
}
