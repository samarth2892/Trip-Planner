package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.util.ArrayList;

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

    public String getOpenTimeString(){
        return this.openTimeString;
    }

    public String getCloseTimeString() {
        return this.closeTimeString;
    }
}