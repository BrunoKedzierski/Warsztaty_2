package pl.coderslab.Model;

import service.DbService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Exercise {
    private int id;
    private String title;
    private String description;
    //Constructors


    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Exercise() {
    }
    //Setters and Getters


    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    //Non-static methods

    public void saveToDb(){
        if (this.id == 0) {
            String query = "INSERT INTO exercise (title,description) VALUES (?,?)";
            List<String> params = new ArrayList<>();
            params.add(this.title);
            params.add(this.description);

            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {

                    this.id = id;

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            String query = "UPDATE users SET  title = ?, description = ?  WHERE id = ?";

            List<String> params = new ArrayList<>();

            params.add(this.title);
            params.add(this.description);
            params.add(String.valueOf(this.id));

            try {

                DbService.executeQuery(query, params);


            } catch (SQLException e) {

                e.printStackTrace();

            }

        }
    }public void delete() {
        if (this.id != 0) {
            String query = "DELETE FROM exercise WHERE id = ?";
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(this.id));
            try {
                DbService.executeQuery(query, params);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.id = 0;

        } else {
            System.out.println("The id: " + this.id + " does not exist");
        }
    }
    //Static methods
    public static Exercise getExerciseById(int id) {
        String query = "SELECT * FROM exercise WHERE id =?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(id));

        try {
            List<String[]> rows = DbService.getData(query, params);
            if (!(rows.size() > 0)) throw new InputMismatchException();
            for (String[] row : rows) {
                Exercise exercise = new Exercise();
                exercise.id = Integer.parseInt(row[0]);
                exercise.title = row[1];
                exercise.description = row[3];
                return exercise;
            }
        } catch (InputMismatchException c){
            System.out.println("Id: " + id  + " does not exist");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Exercise getExerciseByTitle(String title) {
        String query = "SELECT * FROM exercise WHERE title =?";
        ArrayList<String> params = new ArrayList<>();
        params.add(title);

        try {
            List<String[]> rows = DbService.getData(query, params);
            if (!(rows.size() > 0)) throw new InputMismatchException();
            for (String[] row : rows) {
                Exercise exercise = new Exercise();
                exercise.id = Integer.parseInt(row[0]);
                exercise.title = row[1];
                exercise.description = row[2];
                return exercise;
            }
        }catch (InputMismatchException c){
            System.out.println("The exercise: " + title + " does not exist" );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    static public ArrayList<Exercise> loadAllExercise() {
        ArrayList<Exercise> excersises = new ArrayList<>();
        String query = "SELECT * FROM exercise";


        try {
            List<String[]> rows = DbService.getData(query, null);
            for (String[] row : rows) {
                Exercise exercise = new Exercise();
                exercise.id = Integer.parseInt(row[0]);
                exercise.title = row[1];
                exercise.description = row[2];
                excersises.add(exercise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return excersises;
    }




}
