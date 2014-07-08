package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Place {
    private String name;
    private String address;
    private String phoneNumber;
    private String reference;
    private String rating;
    private String website;
    private int openTime;
    private int closeTime;
    private String openTimeString;
    private String closeTimeString;
    private String latitude;
    private String longitude;
    private double priceRange;
    private ArrayList<String> imageURL = new ArrayList<String>();
    private ArrayList<String> reviews = new ArrayList<String>();
    private ArrayList<String> directions = new ArrayList<String>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getOpenTime() {
        return openTime;
    }
    public void setOpenTime(int openTime) {
        this.openTime = openTime;
        double i = openTime / 100;
        openTimeString = (i > 11.59) ? Double.toString(i) + " pm"
                : Double.toString(i) + " am";
    }

    public int getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
        double i = closeTime / 100;
        closeTimeString = (i > 11.59) ? Double.toString(i - 12) + " pm"
                : Double.toString(i) + " am";
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getPriceRange() {
        return priceRange;
    }
    public void setPriceRange(double priceRange) {
        this.priceRange = priceRange;
    }

    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public ArrayList<String> getImageURL() {
        return imageURL;
    }
    public void setImageURL(ArrayList<String> imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }
    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public String getOpenTimeString(){
        try {
            Date openDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", openTime));
            SimpleDateFormat oneDigitFormat = new SimpleDateFormat("h:mm a");
            SimpleDateFormat twoDigitFormat = new SimpleDateFormat("hh:mm a");
            if ((openTime >= 1000 && openTime <= 1259) || openTime >= 2200) {
                return twoDigitFormat.format(openDate);
            } else {
                return oneDigitFormat.format(openDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getCloseTimeString() {
        try {
            Date closeDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", closeTime));
            SimpleDateFormat oneDigitFormat = new SimpleDateFormat("h:mm a");
            SimpleDateFormat twoDigitFormat = new SimpleDateFormat("hh:mm a");
            if ((closeTime >= 1000 && closeTime <= 1259) || closeTime >= 2200) {
                return twoDigitFormat.format(closeDate);
            } else {
                return oneDigitFormat.format(closeDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}