package model.user;
import model.course.Course;

import java.util.*;
public class Student extends User {
    private List<Course > courses = new ArrayList<>();;
    static int idMaker = 1;
    public Student(String name , String password){
        super("STU-",name, password , idMaker);
        ++idMaker;
    }
    @Override
    protected void showMenu() {

    }
    //اضافه کردن درس به لیست دانشجو
}
