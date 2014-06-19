package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.util.ArrayList;

public class Place {
    private String name;
    private String address;
    private String phoneNumber;
    private String reference;
    private int openTime;
    private int closeTime;
    private double latitude;
    private double longitude;
    private double minCost;
    private double maxCost;
    private double rating;
    private ArrayList<String> images;
    private ArrayList<String> reviews;

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

    public int getOpenTime() {
        return openTime;
    }
    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getMinCost() {
        return minCost;
    }
    public void setMinCost(double minCost) {
        this.minCost = minCost;
    }

    public double getMaxCost() {
        return maxCost;
    }
    public void setMaxCost(double maxCost) {
        this.maxCost = maxCost;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getImages() {
        return images;
    }
    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }
    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }
}