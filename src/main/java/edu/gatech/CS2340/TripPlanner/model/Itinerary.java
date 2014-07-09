package main.java.edu.gatech.CS2340.TripPlanner.model;
import java.util.HashMap;

public class Itinerary {
    private String origin;
    private String date;
    private HashMap<Integer,Place> map;

    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<Integer,Place> getMap() {
        return map;
    }
    public void setMap(HashMap map) {
        this.map = map;
    }

}
