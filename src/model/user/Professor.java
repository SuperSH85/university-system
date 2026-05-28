package model.user;
import java.util.*;
public class Professor extends User {
    private ArrayList<String> courses;
    public Professor(String name, String password) {
        super(name, password);
    }
    @Override
    protected void showMenu() {

    }

    //اضافه کردن درس به لیست
}
