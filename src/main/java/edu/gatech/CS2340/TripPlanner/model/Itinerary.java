package main.java.edu.gatech.CS2340.TripPlanner.model;
import java.util.ArrayList;

public class Itinerary {
    private int itineraryId;
    private String origin;
    private String date;
    private ArrayList<Place> places;

    public int getItineraryId() {
        return itineraryId;
    }
    public void setItineraryId() {
        this.itineraryId = itineraryId;
    }

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

    public ArrayList<Place> getPlaces() {
        return places;
    }
    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

}
