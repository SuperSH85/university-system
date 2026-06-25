package model.user;

import exception.InvalidInputException;
import model.Searchable;
import java.util.Scanner;

public abstract class User implements Searchable {
    private String userID;
    private String name;
    private String password;

    public User(String idType ,String name , String password , int idMaker){
        this.userID = idType + (idMaker);
        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return this.userID;
    }

    @Override
    public boolean matches(String keyword) {
        return this.userID.equals(keyword);
    }

    public abstract void showMenu(Scanner scn);
    protected abstract boolean handleMenu(int choice , Scanner scn);

    protected  int safeIntInput(Scanner scn) throws InvalidInputException {
        String input = scn.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input! Please enter a number.");
        }
    }
}
