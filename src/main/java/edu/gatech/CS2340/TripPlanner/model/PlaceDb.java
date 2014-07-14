package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaceDb extends TripPlannerServer {

    public void addItinerary(Itinerary itinerary, String userName) {
        int nextOrderValue = 1;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement placeStatement = conn.createStatement();


            if (itinerary.getId() == 0) {

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
                int next = nextOrderValue + 1;
                String insertPlace =
                        "INSERT INTO itineraries "
                                + "(accountid,userorder,itineraryid,date,startaddress,"
                                + "reference,name,address,phone,opentime,closetime,imageuRL) "
                                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement prep = conn.prepareStatement(insertPlace);
                prep.setInt(1,getUserId(userName));
                prep.setInt(2,j);
                prep.setInt(3,nextOrderValue);
                prep.setString(4, itinerary.getDate());
                prep.setString(5, itinerary.getOrigin());
                prep.setString(6, places[i].getReference());
                prep.setString(7, places[i].getName());
                prep.setString(8, places[i].getAddress());
                prep.setString(9, places[i].getPhoneNumber());
                prep.setInt(10, places[i].getOpenTime());
                prep.setInt(11, places[i].getCloseTime());
                prep.setString(12, places[i].getImageURL().get(0));

                prep.executeUpdate();
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

    public void updateAllItineraryIds(int deletedItineraryId, String userName) {
        String select =
                "SELECT COUNT(DISTINCT(itineraryid)) FROM itineraries WHERE accountid=" + getUserId(userName) +" AND "
                        + "itineraryid > "+ deletedItineraryId +";";
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            ResultSet resultSet = stmt.executeQuery(select);
            if (resultSet.next()) {
                int rows = resultSet.getInt(1);
                int i = deletedItineraryId + 1;
                for (int j = 1; j <= rows; j++) {
                    Statement updateStatement = conn.createStatement();
                    updateStatement.executeUpdate(
                            "UPDATE itineraries "
                                    + "SET itineraryid = itineraryid - 1 WHERE "
                                    + "accountid=" + getUserId(userName) + " AND itineraryid =" + i + ";"
                    );
                    updateStatement.close();
                    i++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}