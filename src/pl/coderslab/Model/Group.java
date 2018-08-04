package pl.coderslab.Model;

import service.DbService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

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

    public int getId() {
        return id;
    }

    //------------
    //Non-Static methods
    public void saveToDb() {
        if (this.id == 0) {
            String query = "INSERT INTO user_group (name) VALUES(?)";
            ArrayList<String> params = new ArrayList<>();
            params.add(this.name);
            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {
                    this.id = id;
                }
            } catch (SQLIntegrityConstraintViolationException c) {
                System.out.println("Group name is already taken");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "UPDATE user_group SET name =? WHERE id =?";
            ArrayList<String> params = new ArrayList<>();
            params.add(this.name);
            params.add(String.valueOf(this.id));
            try {
                DbService.executeQuery(query, params);
            } catch (SQLIntegrityConstraintViolationException c) {
                System.out.println("Group name is already taken");
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    public void delete() {
        if (this.id != 0) {
            String query = "DELETE FROM user_group WHERE id = ?";
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(this.id));
            try {
                DbService.executeQuery(query, params);
                this.id = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Taka grupa nie istnieje!");
        }

    }

    //Static methods
    public static Group getGroupById(int id) {
        String query = "SELECT * FROM user_group WHERE id =?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(id));

        try {
            List<String[]> rows = DbService.getData(query, params);
            if (!(rows.size() > 0)) throw new InputMismatchException();
            for (String[] row : rows) {
                Group group = new Group();
                group.id = Integer.parseInt(row[0]);
                group.name = row[1];
                return group;
            }
        } catch (InputMismatchException c){
            System.out.println("Id: " + id  + " does not exist");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Group getGroupByName(String name) {
        String query = "SELECT * FROM user_group WHERE name =?";
        ArrayList<String> params = new ArrayList<>();
        params.add(name);

        try {
            List<String[]> rows = DbService.getData(query, params);
            if (!(rows.size() > 0)) throw new InputMismatchException();
            for (String[] row : rows) {
                Group group = new Group();
                group.id = Integer.parseInt(row[0]);
                group.name = row[1];
                return group;
            }
        }catch (InputMismatchException c){
            System.out.println("The group: " + name + " does not exist" );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    static public ArrayList<Group> loadAllGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        String query = "SELECT * FROM user_group";


        try {
            List<String[]> rows = DbService.getData(query, null);
            for (String[] row : rows) {
                Group group = new Group();
                group.id = Integer.parseInt(row[0]);
                group.name = row[1];
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

}
