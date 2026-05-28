package model.user;
import java.util.*;
public class Student extends User {
    private ArrayList<String> courses;

    public Student(String name , String password){
        super(name, password);
    }
    @Override
    protected void showMenu() {

    }
    //اضافه کردن درس به لیست دانشجو
}
