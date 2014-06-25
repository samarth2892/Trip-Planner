package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlaceDb extends TripPlannerServer {

    public void saveItinerary(ArrayList<Place> itinerary, int userId) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();

            String insertPlace;
            for (int i = 0; i < itinerary.size(); i++){
                insertPlace =
                        "INSERT INTO itineraries VALUES('"
                                + userId + "','"
                                + itinerary.get(i).getName() + "','"
                                + itinerary.get(i).getAddress() + "','"
                                + itinerary.get(i).getPhoneNumber() + "','"
                                + itinerary.get(i).getOpenTime() + "','"
                                + itinerary.get(i).getCloseTime() + "';";
                stmt.executeUpdate(insertPlace);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Place> loadItinerary(int userId) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            ArrayList<Place> itinerary = new ArrayList<Place>();

            String selectPlaces =
                    "SELECT * FROM itineraries WHERE id='" + userId + "';";
            ResultSet places = placeStatement.executeQuery(selectPlaces);

            Place place;
            while(places.next()) {
                place = new Place();
                place.setName(places.getString(2));
                place.setAddress(places.getString(3));
                place.setPhoneNumber(places.getString(4));
                place.setOpenTime(places.getInt(5));
                place.setCloseTime(places.getInt(6));
                itinerary.add(place);
            }
        return itinerary;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
