package main.java.edu.gatech.CS2340.TripPlanner.model;

/**
 * Created by Charlie on 6/5/2014.
 */
public class Account {

    private String email;
    private String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setPassword() { this.password = password; }
    public String getPassword() { return password; }
}
