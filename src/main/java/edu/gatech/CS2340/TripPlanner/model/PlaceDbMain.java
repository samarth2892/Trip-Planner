package main.java.edu.gatech.CS2340.TripPlanner.model;


import java.util.ArrayList;
import java.util.Random;

public class PlaceDbMain {
    public static void main(String[] args) {
        Random rand = new Random();
        PlaceDb pdb = new PlaceDb();
        pdb.connect();
        Place place = new Place();
        place.setReference("testReference");
        place.setName("testName");
        place.setAddress("testAddress");
        place.setPhoneNumber("1234567890");
        place.setOpenTime(900);
        place.setCloseTime(1700);

        // Test adding contents
        for (int i = 0; i < 5; i++) {
            place.setOpenTime(rand.nextInt(1000)); //Compare to database to verify results are in order
            pdb.addPlace(1, place);
        }
        // Test updating order
        Place place2 = new Place();
        place2.setReference("testReference2");
        place2.setName("testName2");
        place2.setAddress("testAddress2");
        place2.setPhoneNumber("2");
        place2.setOpenTime(902);
        place2.setCloseTime(1702);
        pdb.addPlace(1, place2);
        pdb.updateOrder(1, place, place2);

        // Test viewing contents of an itinerary
        ArrayList<Place> itinerary = pdb.loadItinerary(1);
        for (int i = 0; i < itinerary.size(); i++) {
            System.out.print(itinerary.get(i).getReference() + ", ");
            System.out.print(itinerary.get(i).getName() + ", ");
            System.out.print(itinerary.get(i).getAddress() + ", ");
            System.out.print(itinerary.get(i).getPhoneNumber() + ", ");
            System.out.print(itinerary.get(i).getOpenTime() + ", ");
            System.out.print(itinerary.get(i).getCloseTime() + "\n");
        }
    }
}
