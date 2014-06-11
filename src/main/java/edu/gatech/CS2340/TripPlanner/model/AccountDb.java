package main.java.edu.gatech.CS2340.TripPlanner.model;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sun.misc.BASE64Encoder;

public class AccountDb {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/accounts";
    static final String USER = "root";
    static final String PASS = "";

    private Connection conn;
    private Statement stmt;

    public void connect() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection sucessful.");
            stmt = conn.createStatement();

        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFound: Unable to load driver class.");
            System.exit(1);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encode(String pw) throws Exception {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        }
        try {
            md.update(pw.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage());
        }
        byte raw[] = md.digest();
        String hash = (new BASE64Encoder()).encode(raw);
        return hash;
    }

    public boolean usernameIsInUse(String username) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement accountStatement = conn.createStatement();
            ResultSet id =
                    accountStatement.executeQuery("SELECT id FROM accounts "
                            + "ORDER BY id DESC " + "LIMIT 1;");
            id.next();
            String sql;

            ResultSet existingUser =
                    stmt.executeQuery("SELECT * FROM accounts "
                            + "WHERE user='" + username + "';");
            if (existingUser.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Integer create(String user, String pw, String name) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement accountStatement = conn.createStatement();
            ResultSet id =
                    accountStatement.executeQuery("SELECT id FROM accounts "
                            + "ORDER BY id DESC " + "LIMIT 1;");
            id.next();

            String sql =
                        "INSERT INTO ACCOUNTS " + "VALUES("
                                + (id.getInt(1) + 1) + ",'" + user + "','"
                                + encode(pw) + "','" + name + "');";
                stmt.executeUpdate(sql);

            id =
                    stmt.executeQuery("SELECT id FROM accounts "
                            + "ORDER BY id DESC " + "LIMIT 1;");
            if (id.next()) {
                return (id.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean login(String user, String pass) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet usernameInput =
                    stmt.executeQuery("SELECT * FROM accounts "
                            + "WHERE user='" + user + "';");

            if (usernameInput.next()) {
                try {
                    if (usernameInput.getString("pass").equals(encode(pass))) {
                        System.out.println("Login successful.");
                        return true;
                    } else {
                        // System.out.println(usernameInput.getString("pass"));
                        System.out.println("Wrong password");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Wrong username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void
    update(String user, String pass, String newUser, String newPass) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet updateTarget =
                    stmt.executeQuery("SELECT * FROM accounts "
                            + "WHERE user='" + user + "' " + "AND pass='"
                            + encode(pass) + "';");
            if (updateTarget.next()) {
                stmt.executeUpdate("UPDATE accounts " + "SET user='" + newUser
                        + "', pass ='" + encode(newPass) + "' "
                        + "WHERE user='" + user + "';");
                System.out.println("Update successful.");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}