package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlaceDb extends TripPlannerServer {

    public void addPlace(Itinerary itinerary, Place place) {
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
                            + itinerary.getItineraryId() + ",'"
                            + itinerary.getDate() + "','"
                            + itinerary.getOrigin() + "','"
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
                            "(reference='" + reference + "' AND accountid=" + getUserId() + ");";

            ResultSet exists = stmt.executeQuery(selectReference);
            return exists.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public Itinerary loadItinerary(int itineraryId) {
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            Itinerary itinerary = new Itinerary();
            ArrayList<Place> placeList = new ArrayList<Place>();

            String selectPlaces =
                    "SELECT * FROM itineraries WHERE " +
                            "(accountid=" + getUserId() + " AND itineraryid=" + itineraryId + ") " +
                            "ORDER BY userorder;";
            ResultSet places = placeStatement.executeQuery(selectPlaces);

            Place place;
            while(places.next()) {
                if (itinerary.getDate() == null) {
                    itinerary.setDate(places.getString(4));
                    itinerary.setOrigin(places.getString(5));
                }
                place = new Place();
                place.setReference(places.getString(6));
                place.setName(places.getString(7));
                place.setAddress(places.getString(8));
                place.setPhoneNumber(places.getString(9));
                place.setOpenTime(places.getInt(10));
                place.setCloseTime(places.getInt(11));
                placeList.add(place);
            }
            itinerary.setPlaces(placeList);
            return itinerary;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Itinerary> loadAllItineraries() {

        ArrayList<Itinerary> itineraryList = new ArrayList<Itinerary>();
        String selectUniqueItineraries =
                "SELECT count(DISTINCT(itineraryid)) FROM itineraries WHERE accountid=" + getUserId() +";";
        try {
            ResultSet uniqueItineraries = stmt.executeQuery(selectUniqueItineraries);
            int totalUniqueItineraries = uniqueItineraries.getInt(1);
            for (int i = 1; i <= totalUniqueItineraries; i++) {
                itineraryList.add(loadItinerary(i));
            }
            return itineraryList;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public void updateOrder(Itinerary itinerary, Place place, int targetIndex) {
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            String selectPlaces;

            selectPlaces =
                    "SELECT * FROM itineraries WHERE " +
                            "(accountid=" + getUserId() + " AND reference='" + place.getReference() + "');";
            ResultSet result = placeStatement.executeQuery(selectPlaces);
            result.next();
            int currentOrder = result.getInt(2);

            itinerary.getPlaces().remove(currentOrder);
            itinerary.getPlaces().add(targetIndex, place);

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
    }*/
}