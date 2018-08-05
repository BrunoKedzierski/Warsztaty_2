package pl.coderslab.Administration;

import pl.coderslab.Model.Exercise;
import pl.coderslab.Model.Group;
import pl.coderslab.Model.User;
import service.DbService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExerciseManagment {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        showAllExer();
        manageExercise();

    }

    private static void showAllExer() {
        System.out.println("| id  | title | description|");
        String query = "SELECT * FROM exercise";
        try {
            List<String[]> rows = DbService.getData(query, null);
            for(String [] row : rows){
                int id = Integer.parseInt(row[0]);
                String title = row[1];
                String description = row[2];
                System.out.println("| " + id + " | " + title + " | " + description + " |");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private static void manageExercise() {
        String answer = "";
        do {

            System.out.println("Choose one of the option: Add, Edit, Delete, Close");
            answer = scan.nextLine();
            if (answer.equalsIgnoreCase("Add")) {
                System.out.println("Input title");
                String title = scan.nextLine();
                System.out.println("Input description");
                String description = scan.nextLine();
                System.out.println("Input");
                Exercise exercise = new Exercise(title,description);
                exercise.saveToDb();
                showAllExer();

            }
            if (answer.equalsIgnoreCase("Edit")) {
                System.out.println("Input id of a exercise you want to edit");
                while (!scan.hasNextInt()) {
                    System.out.println("Type a valid id");
                    scan.next();
                }
                int id = scan.nextInt();
                while (Exercise.getExerciseById(id).getId() == -1){
                    System.out.println("This id does not exist. Try again!");
                    id = scan.nextInt();

                }
                scan.nextLine();
                System.out.println("Input title");
                String title = scan.nextLine();
                System.out.println("Input description");
                String description = scan.nextLine();
                Exercise exercise = Exercise.getExerciseById(id);
                exercise.setTitle(title);
                exercise.setDescription(description);
                exercise.saveToDb();
                showAllExer();


            }
            if (answer.equalsIgnoreCase("delete")) {
                System.out.println("Input id of an exercise you want to delete");
                while (!scan.hasNextInt()) {
                    System.out.println("Type a valid id");
                    scan.next();
                }

                int id = scan.nextInt();
                while (Exercise.getExerciseById(id).getId() == -1){
                    System.out.println("This id does not exist. Try again");
                    id = scan.nextInt();

                }

                Exercise exercise = Exercise.getExerciseById(id);
                exercise.delete();
                showAllExer();


            }

        } while (!answer.equalsIgnoreCase("close"));

    }


    }

