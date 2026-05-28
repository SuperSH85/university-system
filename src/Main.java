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
//            do{
//                System.out.println("1. Login");
//                System.out.println("0. Logout");
//                try {
//                    int welcome = scn.nextInt();
//                    if (welcome != 0 || welcome != 1){
//                        throw
//                    }
//                }catch (){
//
//                }
//
//
//            }while (welcome == 1);
            String name ;
            String password;
            do {
                System.out.println("Enter your Name: ");
                name = scn.nextLine();
                System.out.println("Enter your Password: ");
                password = scn.nextLine();
            }while (!isUserExist(name , password));

            User user = findUser(name , password);

            user.showMenu(scn);
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

    private static User findUser(String name , String password){
        User user = null;
        boolean flag = false;
        for (User tempUser : UniversitySystem.getUsers()){
            if (tempUser.getName().equals(name) && tempUser.getPassword().equals(password)){
                user = tempUser;
                flag = true;
                break;
            }
        }
        //if user == null throw exception
        return user;
    }
}
