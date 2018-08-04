package pl.coderslab.Model;

import org.mindrot.BCrypt;
import service.DbService;

import javax.print.DocFlavor;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class User {
    private int id;
    private int person_group_id;
    private String username;
    private String password;
    private String email;

    //Constructors
    public User(String username, String password, String email, int person_group_id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.person_group_id = person_group_id;
        this.id = 0;

        setPassword(password);
    }

    public User() {
    }
    //------------
    //Setters and Getters

    public void setPassword(String password) {

        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPerson_group_id(int person_group_id) {
        this.person_group_id = person_group_id;
    }

    public int getPerson_group_id() {
        return person_group_id;
    }

    public int getId() {
        return id;
    }

    //------------------
    //Non-static methods
    public void saveToDb() {
        if (this.id == 0) {
            String query = "INSERT INTO users (username,email,password,person_group_id) VALUES (?,?,?,?)";
            List<String> params = new ArrayList<>();
            params.add(this.username);
            params.add(this.email);
            params.add(this.password);
            params.add(String.valueOf(this.person_group_id));

            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {

                    this.id = id;

                }
            } catch (SQLIntegrityConstraintViolationException c) {
                System.out.println("Email address is already used or the group does not exist");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            String query = "UPDATE users SET  username = ?, password = ?, email = ?, person_group_id = ? WHERE id = ?";

            List<String> params = new ArrayList<>();


            params.add(this.username);

            params.add(this.password);

            params.add(this.email);
            params.add(String.valueOf(this.person_group_id));


            params.add(String.valueOf(this.id));

            try {

                DbService.executeQuery(query, params);


            } catch (SQLIntegrityConstraintViolationException c) {
                System.out.println("Email address is already used or the group does not exist");

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }
    }

    public void delete() {
        if (this.id != 0) {
            String query = "DELETE FROM users WHERE id = ?";
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
    //----------------------
    //Static methods

    public static User getUserById(int id) {

        String query = "SELECT * FROM users WHERE id =?";
        ArrayList<String> params = new ArrayList<>();
        params.add(String.valueOf(id));

        try {
            List<String[]> rows = DbService.getData(query, params);
            if (!(rows.size() > 0)) throw new InputMismatchException();
            for (String[] row : rows) {
                User user = new User();
                user.id = Integer.parseInt(row[0]);
                user.email = row[1];
                user.username = row[2];
                user.password = row[3];
                user.person_group_id = Integer.parseInt(row[4]);


                return user;
            }
        }  catch (InputMismatchException e) {
            System.out.println("Id: " + id + " does not exist");
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static User getUserByMail(String email) {
        String query = "SELECT * FROM users WHERE email =?";
        ArrayList<String> params = new ArrayList<>();
        params.add(email);

        try {
            List<String[]> rows = DbService.getData(query, params);
            if (!(rows.size() > 0)) throw new InputMismatchException();

            for (String[] row : rows) {
                User user = new User();
                user.id = Integer.parseInt(row[0]);
                user.email = row[1];
                user.username = row[2];
                user.password = row[3];
                user.person_group_id = Integer.parseInt(row[4]);


                return user;
            }
        } catch (InputMismatchException c){
            System.out.println("email: " + email + " does not exist");


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }


    static public ArrayList<User> loadAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";


        try {
            List<String[]> rows = DbService.getData(query, null);
            for (String[] row : rows) {
                User user = new User();
                user.id = Integer.parseInt(row[0]);
                user.email = row[1];
                user.username = row[2];
                user.password = row[3];
                user.person_group_id = Integer.parseInt(row[4]);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


}
