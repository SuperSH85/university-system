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

            System.out.println("Enter your Name: ");
            String name = scn.nextLine();
            System.out.println("Enter your Password: ");
            String password = scn.nextLine();
        }
    }
}
