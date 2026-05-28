package model.user;

import database.UniversitySystem;
import model.course.Course;

import java.util.Scanner;

public class Admin extends User{
    private static int idMaker = 1;
    public Admin(String name, String password) {
        super("A-",name, password , idMaker++);
    }
    @Override
    public int showMenu(Scanner scn) {
        System.out.println("===== ADMIN MENU =====");
        System.out.println("1. Add New Course");
        System.out.println("2. Add New User");
        System.out.println("0. Logout");
        System.out.println("======================");
        int choice = scn.nextInt();
        //throw invalid  if choice not between the options or not int
        return choice;
    }

    public void addCourse(Course course){
        UniversitySystem.create(course);
    }

    public void addUser(User user){
        UniversitySystem.create(user);
    }
}
