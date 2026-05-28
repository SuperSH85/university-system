package model.user;
import model.course.Course;

import java.util.*;
public class Student extends User {
    private List<Course > courses = new ArrayList<>();;

    public Student(String name , String password){
        super("STU-",name, password);
    }
    @Override
    protected void showMenu() {

    }
    //اضافه کردن درس به لیست دانشجو
}
