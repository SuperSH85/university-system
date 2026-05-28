import database.UniversitySystem;
import model.user.User;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        var scn = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║       UNIVERSITY ENROLLMENT SYSTEM      ║");
            System.out.println("║          Welcome! Please Login          ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println();
            String name ;
            String password;
            do {
                System.out.println("Enter your Name: ");
                name = scn.nextLine();
                System.out.println("Enter your Password: ");
                password = scn.nextLine();
            }while (isUserExist(name , password));
        }
    }

    private static boolean isUserExist(String name , String password){
        boolean flag = false;
        for (User user : UniversitySystem.getUsers()){
            if (user.getName().equals(name) && user.getPassword().equals(password)){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
