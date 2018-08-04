package pl.coderslab.Model;

import service.DbService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;
    private int user_id;
    private int exercise_id;

    //Constructors


    public Solution(String description, int user_id, int exercise_id) {
        this.description = description;
        this.user_id = user_id;
        this.exercise_id = exercise_id;
    }

    public Solution() {
    }


    //Setters and Getters

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getCreated() {
        return created;
    }


    public String getUpdated() {
        return updated;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    // Non-static methods


    public void saveToDb() {
        if (this.id == 0) {
            String query = "INSERT INTO solution (created,description,users_id,excercise_id) VALUES(NOW(),?,?,?)";
            ArrayList<String> params = new ArrayList<>();
            params.add(this.description);
            params.add(String.valueOf(this.user_id));
            params.add(String.valueOf(this.exercise_id));
            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {
                    this.id = id;
                }
            } catch (SQLIntegrityConstraintViolationException c) {
                System.out.println("Error while adding solution to database. Please check if user and exercise id are correct!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "UPDATE solution SET updated = NOW(), description = ?, users_id = ? , excercise_id = ? WHERE id =?";
            ArrayList<String> params = new ArrayList<>();
            params.add(this.description);
            params.add(String.valueOf(this.user_id));
            params.add(String.valueOf(this.exercise_id));
            try {
                DbService.executeQuery(query, params);
            } catch (SQLIntegrityConstraintViolationException c) {
                System.out.println("Error while updating solution. Please check if user and exercise id are correct!");
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    public void delete() {
        if (this.id != 0) {
            String query = "DELETE FROM solution WHERE id = ?";
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(this.id));
            try {
                DbService.executeQuery(query, params);
                this.id = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("The solution does not exist");
        }

    }
    //Static methods

    public static Solution getSolutionById(int id) {
        String query = "SELECT * FROM solution WHERE id =?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(id));

        try {
            List<String[]> rows = DbService.getData(query, params);
            if (!(rows.size() > 0)) throw new InputMismatchException();
            for (String[] row : rows) {
                Solution solution = new Solution();
                solution.id = Integer.parseInt(row[0]);
                solution.created = row[1];
                solution.updated = row[2];
                solution.description = row[3];
                solution.user_id = Integer.parseInt(row[4]);
                solution.exercise_id = Integer.parseInt(row[5]);
                return solution;
            }
        } catch (InputMismatchException c){
            System.out.println("Id: " + id  + " does not exist");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    static public ArrayList<Solution> loadAllGroups() {
        ArrayList<Solution> solutions = new ArrayList<>();
        String query = "SELECT * FROM solution";


        try {
            List<String[]> rows = DbService.getData(query, null);
            for (String[] row : rows) {
                Solution solution = new Solution();
                solution.id = Integer.parseInt(row[0]);
                solution.created = row[1];
                solution.updated = row[2];
                solution.description = row[3];
                solution.user_id = Integer.parseInt(row[4]);
                solution.exercise_id = Integer.parseInt(row[5]);
                solutions.add(solution);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solutions;
    }

}
