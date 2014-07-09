package main.java.edu.gatech.CS2340.TripPlanner.model;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public abstract class TripPlannerServer {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/accounts";
    static final String USER = "root";
    static final String PASS = "admin";

    protected Connection conn;
    protected Statement stmt;
    protected static String currentUser;

    public void connect() {
        try {
            Class.forName(DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection successful.");
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
                            "accountid int NOT NULL, " +
                            "userorder int NOT NULL, " +
                            "itineraryid int NOT NULL, " +
                            "date varchar(255) NOT NULL, " +
                            "startaddress varchar(255) NOT NULL, " +
                            "reference varchar(255) NOT NULL, " +
                            "name varchar(255) NOT NULL, " +
                            "address varchar(255) NOT NULL, " +
                            "phone varchar(255) NOT NULL, " +
                            "opentime int NOT NULL, " +
                            "closetime int NOT NULL, " +
                            "PRIMARY KEY (accountid, userorder, itineraryid));";
            stmt.execute(createItinerariesTable);

            String initItineraryDb =
                    "INSERT INTO itineraries VALUES (" +
                            "1, 1, 1, 'init', 'init', 'init', 'init', 'init', 'init', 0, 0)" +
                            "ON DUPLICATE KEY UPDATE accountid=1;";
            stmt.execute(initItineraryDb);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUserId(String userName) {
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String selectUser =
                    "SELECT id FROM accounts.accounts WHERE user='" + userName + "';";
            ResultSet findUser = stmt.executeQuery(selectUser);
            if (findUser.next()) {
                return findUser.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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
