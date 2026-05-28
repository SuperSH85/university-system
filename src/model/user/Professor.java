package model.user;
import model.course.Course;

import java.util.*;
public class Professor extends User {
    private List<Course> courses = new ArrayList<>();;
    public Professor(String name, String password) {
        super("PRO-",name, password);
    }
    @Override
    protected void showMenu() {

    }

    //اضافه کردن درس به لیست

}
