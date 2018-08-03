package pl.coderslab.Model;

import service.DbService;

import java.sql.SQLException;
import java.util.ArrayList;

public class Group {
    private int id;
    private String name;
    //Constructors
    public Group() {
    }

    public Group(String name) {
        this.name = name;
        this.id = 0;
    }
    //-----------
    //Setters and Getters


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    //------------
    //Non-Static methods
    public void saveToDb(){
        if(this.id == 0) {
            String query = "INSERT INTO user_group (name) VALUES(?)";
            ArrayList<String> params = new ArrayList<>();
            params.add(this.name);
            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {
                    this.id = id;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String query = "UPDATE user_group SET name =? WHERE id =?";
            ArrayList<String> params = new ArrayList<>();
            params.add(this.name);
            params.add(String.valueOf(this.id));
            try {
                DbService.executeQuery(query,params);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }
    public void delete(){
        if(this.id != 0){
            String query = "DELETE FROM user_group WHERE id = ?";
            ArrayList <String> params = new ArrayList<>();
            params.add(String.valueOf(this.id));
            try {
                DbService.executeQuery(query,params);
                this.id = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Taka grupa nie istnieje!");
        }

    }
    //Static methods
}
