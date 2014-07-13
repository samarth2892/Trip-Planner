package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaceDb extends TripPlannerServer {

    public void addItinerary(Itinerary itinerary, String userName) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            int nextOrderValue;

            if(itinerary.getId() == 0) {
                ResultSet itinerarySizeResult = placeStatement.executeQuery(
                        "SELECT * FROM itineraries WHERE accountid='" + getUserId(userName) + "' ORDER BY itineraryid DESC;");

                if (itinerarySizeResult.next()) {
                    nextOrderValue = itinerarySizeResult.getInt(3) + 1;
                } else {
                    nextOrderValue = 1;
                }

            } else {
                clearItinerary(userName, itinerary.getId());
                nextOrderValue = itinerary.getId();
            }

            Place[] places = itinerary.getOrderedPlacesArray();

            for (int i = 0; i < places.length; i++) {
                int j = i + 1;
                String insertPlace =
                        "INSERT INTO itineraries VALUES("
                                + getUserId(userName) + ","
                                + j + ","
                                + nextOrderValue + ",'"
                                + itinerary.getDate() + "','"
                                + itinerary.getOrigin() + "','"
                                + places[i].getReference() + "','"
                                + places[i].getName() + "','"
                                + places[i].getAddress() + "','"
                                + places[i].getPhoneNumber() + "',"
                                + places[i].getOpenTime() + ","
                                + places[i].getCloseTime() + ",'"
                                + places[i].getImageURL().get(0) + "');";
                stmt.executeUpdate(insertPlace);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean placeExists(Place place, String userName) {
        try {

            String reference = place.getReference();

            String selectReference =
                    "SELECT * FROM itineraries WHERE " +
                            "(reference='" + reference + "' AND accountid=" + getUserId(userName) + ");";

            ResultSet exists = stmt.executeQuery(selectReference);
            return exists.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public Itinerary loadItinerary(int itineraryId, String userName) {

        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            Itinerary itinerary = new Itinerary();

            HashMap<String,Place> placesInItinerary = new HashMap<String, Place>();

            String selectPlaces =
                    "SELECT * FROM itineraries WHERE " +
                            "(accountid=" + getUserId(userName) + " AND itineraryid=" + itineraryId + ") " +
                            "ORDER BY userorder;";
            ResultSet places = placeStatement.executeQuery(selectPlaces);
            while (places.next()) {
                    itinerary.setDate(places.getString(4));
                    itinerary.setOrigin(places.getString(5));
                    Place place = new Place();
                    place.setReference(places.getString(6));
                    place.setName(places.getString(7));
                    place.setAddress(places.getString(8));
                    place.setPhoneNumber(places.getString(9));
                    place.setOpenTime(places.getInt(10));
                    place.setCloseTime(places.getInt(11));
                    String image = places.getString(12);
                    ArrayList<String> s = new ArrayList<String>();
                    s.add(0,image);
                    place.setImageURL(s);
                    placesInItinerary.put(Integer.toString(places.getInt(2)), place);
                }

                itinerary.setId(itineraryId);
                itinerary.setMap(placesInItinerary);
                return itinerary;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Itinerary> loadAllItineraries(String userName) {

        ArrayList<Itinerary> allItineraries = new ArrayList<Itinerary>();
        String selectUniqueDates =
                "SELECT count(DISTINCT(itineraryid)) FROM itineraries WHERE accountid=" + getUserId(userName) +";";
        try {
            ResultSet uniqueDates = stmt.executeQuery(selectUniqueDates);
            if(uniqueDates.next()) {
                int totalUniqueDates = uniqueDates.getInt(1);

                for (int i = 1; i <= totalUniqueDates; i++) {
                    allItineraries.add(loadItinerary(i, userName));
                }
                return allItineraries;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Itinerary>();
    }

    public boolean hasItinerary(String userName, int itineraryId){

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            String selectItinerary;

            selectItinerary = "SELECT * FROM itineraries WHERE " +
                    "accountid="+getUserId(userName)+ " AND itineraryid=" + itineraryId + ";";

            ResultSet result1 = placeStatement.executeQuery(selectItinerary);

            return result1.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clearItinerary(String userName, int itineraryID) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement deleteStatement = conn.createStatement();
            String deleteItinerary = "DELETE FROM itineraries WHERE" +
                    " accountid="+getUserId(userName)+" AND itineraryid="+itineraryID+";";

            deleteStatement.executeUpdate(deleteItinerary);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Place place1, Place place2, String userName) {
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();
            String selectPlaces;

            selectPlaces =
                    "SELECT * FROM itineraries WHERE " +

                            "(accountid=" + getUserId(userName) + " AND reference='" + place1.getReference() + "');";
            ResultSet result1 = placeStatement.executeQuery(selectPlaces);
            result1.next();
            int userOrder1 = result1.getInt(2);

            selectPlaces =
                    "SELECT * FROM itineraries WHERE " +
                            "(accountid=" + getUserId(userName) + " AND reference='" + place2.getReference() + "');";
            ResultSet result2 = placeStatement.executeQuery(selectPlaces);
            result2.next();
            int userOrder2 = result2.getInt(2);

            placeStatement.executeUpdate(
                    "UPDATE itineraries " +
                            "SET userorder=0 WHERE " +
                            "(accountid=" + getUserId(userName) + " AND userorder=" + userOrder1 + ");"
            );

            placeStatement.executeUpdate(
                    "UPDATE itineraries " +
                            "SET userorder=" + userOrder1 + " WHERE " +
                            "(accountid=" + getUserId(userName) + " AND userorder=" + userOrder2 + ");"
            );

            placeStatement.executeUpdate(
                    "UPDATE itineraries " +
                            "SET userorder=" + userOrder2 + " WHERE " +
                            "(accountid=" + getUserId(userName) + " AND userorder=0);"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String...args) {
        PlaceDb p = new PlaceDb();
        p.connect();

        Place[] places = new Place[4];
        for (int i = 0; i < places.length; i++) {
            Place place = new Place();
            place.setName("Test1");
            place.setReference("Test1");
            place.setAddress("Test1");
            place.setPhoneNumber("Test1");
            place.setOpenTime(1);
            place.setCloseTime(2);
            String image = "test1";
            ArrayList<String> s = new ArrayList<String>();
            s.add(0,image);
            place.setImageURL(s);
            places[i] = place;
        }

        Itinerary itinerary = new Itinerary();
        itinerary.setId(2);
        itinerary.setDate("Test1");
        itinerary.setOrigin("Test1");
        itinerary.setOrderedPlacesArray(places);

        p.addItinerary(itinerary,"admin");


    }
}