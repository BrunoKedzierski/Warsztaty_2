package pl.coderslab.Administration;

import pl.coderslab.Model.Group;
import pl.coderslab.Model.User;
import service.DbService;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserManagment {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        showAllUsers();
        manageUsers();

    }

    private static void showAllUsers() {
        System.out.println("id | username | email | group id");
        System.out.println("");
        String query = "SELECT * FROM users";
        try {
            List<String[]> rows = DbService.getData(query, null);
            for (String[] row : rows) {
                int id = Integer.parseInt(row[0]);
                String username = row[1];
                String email = row[2];
                int group_id = Integer.parseInt(row[4]);
                System.out.println(id + "] " + username + " | " + email + " | " + group_id + " |");


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void manageUsers() {
        String answer = "";
        do {

                System.out.println("Choose one of the option: Add, Edit, Delete, Close");
                answer = scan.nextLine();
                if (answer.equalsIgnoreCase("Add")) {
                    System.out.println("Input username");
                    String username = scan.nextLine();
                    System.out.println("Input email");
                    String email = scan.nextLine();
                    System.out.println("Input password");
                    String password = scan.nextLine();
                    System.out.println("Input group id");
                    while (!scan.hasNextInt()) {
                        System.out.println("Type a valid id");
                        scan.next();
                    }
                    int group = scan.nextInt();
                    while (Group.getGroupById(group).getId() == -1){
                        System.out.println("This id does not exist. Try again!");
                        group = scan.nextInt();

                    }

                    User user = new User(username, password, email, group);
                    user.saveToDb();
                    showAllUsers();
                    scan.nextLine();
                }
                if (answer.equalsIgnoreCase("Edit")) {
                    System.out.println("Input id of a user you want to edit");
                    while (!scan.hasNextInt()) {
                        System.out.println("Type a valid id");
                        scan.next();
                    }
                    int id = scan.nextInt();
                    while (User.getUserById(id).getId() == -1){
                        System.out.println("This id does not exist. Try again!");
                        id = scan.nextInt();

                    }
                    scan.nextLine();
                    System.out.println("Input username");
                    String username = scan.nextLine();
                    System.out.println("Input email");
                    String email = scan.nextLine();
                    System.out.println("Input password");
                    String password = scan.nextLine();
                    System.out.println("Input group id");
                    while (!scan.hasNextInt()) {
                        System.out.println("Type a valid id");
                        scan.next();
                    }
                    int group = scan.nextInt();
                    while (Group.getGroupById(group).getId() == -1){
                        System.out.println("This id does not exist. Try again!");
                        group = scan.nextInt();

                    }



                    User user = User.getUserById(id);
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setPerson_group_id(group);
                    user.saveToDb();
                    showAllUsers();
                    scan.nextLine();

                }
                if (answer.equalsIgnoreCase("delete")) {
                    System.out.println("Input id of an user you want to delete");
                    while (!scan.hasNextInt()) {
                        System.out.println("Type a valid id");
                        scan.next();
                    }

                    int id = scan.nextInt();
                    while (User.getUserById(id).getId() == -1){
                        System.out.println("This id does not exist. Try again");
                        id = scan.nextInt();

                    }

                    User user = User.getUserById(id);
                    user.delete();
                    showAllUsers();
                    scan.nextLine();

                }

        } while (!answer.equalsIgnoreCase("close"));

    }
}
