package main.java.edu.gatech.CS2340.TripPlanner.model;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;


public class TripPlannerServer {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/accounts";
    static final String USER = "root";
    static final String PASS = "admin";

    protected Connection conn;
    protected Statement stmt;

    public void connect() {
        try {
            Class.forName(DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection sucessful.");
            stmt = conn.createStatement();
            makeTables();
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFound: Unable to load driver class.");
            System.exit(1);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeTables() {
        try {

            String createAccountsTable =
                    "CREATE TABLE IF NOT EXISTS accounts ( " +
                            "id int NOT NULL AUTO_INCREMENT, " +
                            "user varchar(255) NOT NULL, " +
                            "pass varchar(255) NOT NULL, " +
                            "name varchar(255) NOT NULL, " +
                            "PRIMARY KEY (id));";
            stmt.execute(createAccountsTable);

            String initAccountDb =
                    "INSERT INTO accounts VALUES (" +
                            "1, 'admin', '0DPiKuNIrrVmD8IUCuw1hQxNqZc=', 'admin')" +
                            "ON DUPLICATE KEY UPDATE id=1;";
            stmt.execute(initAccountDb);

            String createItinerariesTable =
                    "CREATE TABLE if not exists itineraries ( " +
                            "accountid int(255) NOT NULL, " +
                            "name varchar(255) NOT NULL, " +
                            "address varchar(255) NOT NULL, " +
                            "phone varchar(255) NOT NULL, " +
                            "opentime varchar(255) NOT NULL, " +
                            "closetime varchar(255) NOT NULL, " +
                            "PRIMARY KEY (accountid));";
            stmt.execute(createItinerariesTable);

            String initItineraryDb =
                    "INSERT INTO itineraries VALUES (" +
                            "1, 'init', 'init', 'init', 'init', 'init')" +
                            "ON DUPLICATE KEY UPDATE accountid=1;";
            stmt.execute(initItineraryDb);

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
        byte[] raw = md.digest();
        String hash = (new BASE64Encoder()).encode(raw);
        return hash;
    }
}