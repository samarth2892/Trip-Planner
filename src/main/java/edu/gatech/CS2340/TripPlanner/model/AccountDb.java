package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AccountDb extends TripPlannerServer {

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
        return true;
    }

    public Integer create(String username, String password, String name) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement accountStatement = conn.createStatement();
            ResultSet id =
                    accountStatement.executeQuery("SELECT id FROM accounts "
                            + "ORDER BY id DESC " + "LIMIT 1;");
            id.next();

            String insertAccount =
                        "INSERT INTO accounts (user, pass, name) VALUES(('"
                                + username + "'),('"
                                + encode(password) + "'),('"
                                + name + "'));";
            stmt.executeUpdate(insertAccount);

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
                    if (usernameInput.getString("pass")
                            .equals(encode(password))) {
                        currentUser = username;
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
                stmt.executeUpdate("UPDATE accounts "
                        + "SET user='" + newUsername + "'"
                        + "WHERE user='" + oldUsername + "';");
                System.out.println("Update successful.");
                currentUser = newUsername;
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
                stmt.executeUpdate("UPDATE accounts "
                        + "SET pass='" + encode(newPassword) + "'"
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
}