package pl.coderslab.Model;

import org.mindrot.BCrypt;
import service.DbService;

import javax.print.DocFlavor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = 0;
        setPassword(password);
    }

    public User() {
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void saveToDb() {
        if (this.id == 0) {
            String query = "INSERT INTO Users (username,email,password) VALUES (?,?,?)";
            List<String> params = new ArrayList<>();
            params.add(this.username);
            params.add(this.email);
            params.add(this.password);

            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {

                    this.id = id;

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            String query = "UPDATE Users SET  username = ?, password = ?, email = ? WHERE id = ?";

            List<String> params = new ArrayList<>();


            params.add(this.username);

            params.add(this.password);

            params.add(this.email);

            params.add(String.valueOf(this.id));

            try {

                DbService.executeQuery(query,params);


            } catch (SQLException e) {

                e.printStackTrace();

            }

        }
        }


    public static User getUserById(int id){

        String query = "SELECT * FROM Users WHERE id =?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(id));

        try {
            List<String[] > rows = DbService.getData(query,params);
            for(String[] row : rows){
                User user = new User();
                user.id = Integer.parseInt(row[0]);
                user.email = row[1];
                user.username = row[2];
                user.password = row[3];
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEmail() {
        return email;
    }
    static public ArrayList<User> loadAllUsers(){
        ArrayList <User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";


        try {
            List<String[] > rows = DbService.getData(query,null);
            for(String[] row : rows){
                User user = new User();
                user.id = Integer.parseInt(row[0]);
                user.email = row[1];
                user.username = row[2];
                user.password = row[3];
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void delete(){
        if(this.id != 0){
            String query = "DELETE FROM Users WHERE id = ?";
            ArrayList <String> params = new ArrayList<>();
            params.add(String.valueOf(this.id));
            try {
                DbService.executeQuery(query,params);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.id = 0;

        }
    }
}
