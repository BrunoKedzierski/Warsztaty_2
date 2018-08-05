package pl.coderslab.UserApp;

import org.mindrot.BCrypt;
import pl.coderslab.Model.User;

import java.util.Scanner;

public class UserApp {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
    checkPass();
    }

    private static void checkPass(){
        System.out.println("Insert user email");
        String email = scan.nextLine();
        while (User.getUserByMail(email).getEmail() == ""){
            System.out.println("This email does not exist. Try again");
            email = scan.nextLine();

        }
        System.out.println("Insert password");
        String password = scan.nextLine();
        User user = User.getUserByMail(email);

        if(BCrypt.checkpw(password,user.getPassword())){
            System.out.println("Loging complete");
        }
        else{
            System.out.println("Wrong password");
        }
    }
}
