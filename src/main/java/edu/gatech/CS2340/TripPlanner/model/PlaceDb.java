package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlaceDb extends TripPlannerServer {

    public void addPlace(Place place, String date) {
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            int nextOrderValue;

            ResultSet itinerarySizeResult = placeStatement.executeQuery(
                    "SELECT * FROM itineraries WHERE accountid=" + getUserId() + " ORDER BY userorder DESC;");
            if (itinerarySizeResult.next()) {
                nextOrderValue = itinerarySizeResult.getInt(2) + 1;
            } else {
                nextOrderValue = 1;
            }

            String insertPlace =
                    "INSERT INTO itineraries VALUES("
                            + getUserId() + ","
                            + nextOrderValue + ",'"
                            + date + ",'"
                            + place.getReference() + "','"
                            + place.getName() + "','"
                            + place.getAddress() + "','"
                            + place.getPhoneNumber() + "',"
                            + place.getOpenTime() + ","
                            + place.getCloseTime() + ");";
            stmt.executeUpdate(insertPlace);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean placeExists(Place place) {
        try {

            String reference = place.getReference();
            String selectReference =
                    "SELECT * FROM itineraries WHERE " +
                            "(reference='" + reference + "' AND userid=" + getUserId() + ");";
            ResultSet exists = stmt.executeQuery(selectReference);
            return exists.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public ArrayList<Place> loadItinerary() {
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            ArrayList<Place> itinerary = new ArrayList<Place>();

            String selectPlaces =
                    "SELECT * FROM itineraries WHERE accountid='" + getUserId() + "' ORDER BY userorder;";
            ResultSet places = placeStatement.executeQuery(selectPlaces);

            Place place;
            while(places.next()) {
                place = new Place();
                place.setReference(places.getString(3));
                place.setName(places.getString(4));
                place.setAddress(places.getString(5));
                place.setPhoneNumber(places.getString(6));
                place.setOpenTime(places.getInt(7));
                place.setCloseTime(places.getInt(8));
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

    public void updateOrder(Place place1, Place place2) {
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            String selectPlaces;

            selectPlaces =
                    "SELECT * FROM itineraries WHERE " +
                            "(accountid=" + getUserId() + " AND reference='" + place1.getReference() + "');";
            ResultSet result1 = placeStatement.executeQuery(selectPlaces);
            result1.next();
            int userOrder1 = result1.getInt(2);

            selectPlaces =
                    "SELECT * FROM itineraries WHERE " +
                            "(accountid=" + getUserId() + " AND reference='" + place2.getReference() + "');";
            ResultSet result2 = placeStatement.executeQuery(selectPlaces);
            result2.next();
            int userOrder2 = result2.getInt(2);

            placeStatement.executeUpdate(
                    "UPDATE itineraries " +
                            "SET userorder=0 WHERE " +
                            "(accountid=" + getUserId() + " AND userorder=" + userOrder1 + ");"
            );

            placeStatement.executeUpdate(
                    "UPDATE itineraries " +
                            "SET userorder=" + userOrder1 + " WHERE " +
                            "(accountid=" + getUserId() + " AND userorder=" + userOrder2 + ");"
            );

            placeStatement.executeUpdate(
                    "UPDATE itineraries " +
                            "SET userorder=" + userOrder2 + " WHERE " +
                            "(accountid=" + getUserId() + " AND userorder=0);"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}