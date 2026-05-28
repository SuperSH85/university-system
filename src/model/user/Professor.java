package model.user;
import model.course.Course;

import java.util.*;
public class Professor extends User {
    static int idMaker = 1;
    private List<Course> courses = new ArrayList<>();;
    public Professor(String name, String password) {
        super("PRO-",name, password , idMaker);
        ++idMaker;
    }
    @Override
    protected void showMenu() {

    }

    //اضافه کردن درس به لیست

}
