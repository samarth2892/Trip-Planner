package main.java.edu.gatech.CS2340.TripPlanner.model;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AccountDb {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/accounts";
    static final String USER = "root";
    static final String PASS = "admin";

    private Connection conn;
    private Statement stmt;

    public void connect() {
        try {

            Class.forName(DRIVER);
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

    public boolean usernameIsInUse(String username) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement accountStatement = conn.createStatement();
            ResultSet id =
                    accountStatement.executeQuery("SELECT id FROM accounts "
                            + "ORDER BY id DESC " + "LIMIT 1;");
            id.next();
            ResultSet existingUser =
                    stmt.executeQuery("SELECT * FROM accounts "
                            + "WHERE user='" + username + "';");
            return (existingUser.next());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Integer create(String username, String password, String name) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement accountStatement = conn.createStatement();
            ResultSet id =
                    accountStatement.executeQuery("SELECT id FROM accounts "
                            + "ORDER BY id DESC " + "LIMIT 1;");
            id.next();

            String sql =
                        "INSERT INTO ACCOUNTS " + "VALUES("
                                + (id.getInt(1) + 1) + ",'" + username + "','"
                                + encode(password) + "','" + name + "');";
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

    public boolean login(String username, String password) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet usernameInput =
                    stmt.executeQuery("SELECT * FROM accounts "
                            + "WHERE user='" + username + "';");

            if (usernameInput.next()) {
                try {
                    if (usernameInput.getString("pass").equals(encode(password))) {
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
    updateUsername(String oldUsername, String password, String newUsername) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet updateTarget =
                    stmt.executeQuery("SELECT * FROM accounts "
                            + "WHERE user='" + oldUsername + "' " + "AND pass='"
                            + encode(password) + "';");
            if (updateTarget.next()) {
                stmt.executeUpdate("UPDATE accounts " + "SET user='" + newUsername + "'"
                        + "WHERE user='" + oldUsername + "';");
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

    public void
    updatePassword(String username, String oldPassword, String newPassword) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet updateTarget =
                    stmt.executeQuery("SELECT * FROM accounts "
                            + "WHERE user='" + username + "' " + "AND pass='"
                            + encode(oldPassword) + "';");
            if (updateTarget.next()) {
                stmt.executeUpdate("UPDATE accounts " + "SET pass='" + encode(newPassword) + "'"
                        + "WHERE user='" + username + "';");
                System.out.println("Update successful.");
            } else {
                System.out.println("Invalid password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encode(String pw) throws Exception {
        MessageDigest md;
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
}