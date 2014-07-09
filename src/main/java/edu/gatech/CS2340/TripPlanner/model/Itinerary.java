package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.util.HashMap;

public class Itinerary {

    private int id = 0;
    private String origin;
    private String date;
    private HashMap<String,Place> map;
    private Place[] orderedPlacesArray;

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

    public HashMap<String,Place> getMap() {
        return map;
    }
    public void setMap(HashMap<String,Place> map) {
        this.map = map;
    }

    public void setOrderedPlacesArray(Place[] orderedPlacesArray) {
        this.orderedPlacesArray = orderedPlacesArray;
    }
    public Place[] getOrderedPlacesArray(){
        return orderedPlacesArray;
    }

    public void setId(int id){this.id = id;}
    public int getId(){return  this.id;}
}
