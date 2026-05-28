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
    public void showMenu(Scanner scn) {
        System.out.println("===== ADMIN MENU =====");
        System.out.println("1. Add New Course");
        System.out.println("2. Add New User");
        System.out.println("0. Logout");
        System.out.println("======================");
    }

    @Override
    public void handleMenu(int choice) {

    }

    public void addCourse(Course course){
        UniversitySystem.create(course);
    }

    public void addUser(User user){
        UniversitySystem.create(user);
    }
}
