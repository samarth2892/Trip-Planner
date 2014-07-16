package main.java.edu.gatech.CS2340.TripPlanner.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class GooglePlaceSearch {
    final static String KEY = "AIzaSyAekNru_w4ZwcjbMfMXwVK-TnFLtj4TQUM";

    private String googleAPIURL = "https://maps.googleapis.com/maps/api";
    private String address = "";
    private String keyword = "Attractions";
    private String minPrice = "0";
    private double minRating = 1.0;
    private double radius = 5000;
    private double startHour = 0;
    private double endHour = 2359;
    private int day = 0;

    private Object latitude;
    private Object longitude;
    private HttpClient client = HttpClientBuilder.create().build();
    private HttpResponse response;
    private HttpEntity entity;

    public GooglePlaceSearch(String address,int day) {
        this.address = (!(null == address || address.equals("")))
                ? address : "";
        this.day = ((day > 0 && day < 7) ? day : 0);
    }

    public void setMinPrice(String minPrice ){
        this.minPrice = (!(null == minPrice || minPrice.equals("")))
                ? minPrice : "0";
    }
    public void setKeyword(String keyword) {
        this.keyword = (!(null == keyword || keyword.equals("")))
                ? keyword: "Attractions";
    }
    public void setMinRating(double minRating) {
        this.minRating = (minRating > 0 && minRating < 5)?
             minRating : 1.0;
    }

    public void setDay(int day) {
        this.day = ((day > 0 && day < 7) ? day : 1);
    }

    public void setRadiusInMeters(double radius) {
        this.radius =  (radius > 0 && radius < 50000)
                ? radius : 5000;
    }

    public void setStartEndHour(double startHour, double endHour) {
        this.startHour = (startHour >= 0)
                ? startHour : 0;
        this.endHour = (endHour <= 2359)
                ? endHour : 2359;
    }

    public ArrayList<Place> search() throws MalformedURLException {
        ArrayList<Place> places = new ArrayList<Place>();
        try {
            generateGeocode();
            places = generatePlaces();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }

    public void generateGeocode() throws Exception {

        response = client.execute(new HttpGet(googleAPIURL
                + "/geocode/json?address=" + this.address
                + "&key=" + KEY));

        entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject jsonStringResult = new JSONObject(responseString);
        JSONArray locationDetails = jsonStringResult.getJSONArray("results");
        JSONObject geometry = locationDetails.getJSONObject(0)
                .getJSONObject("geometry");
        this.latitude = geometry
                .getJSONObject("location").get("lat");
        this.longitude = geometry
                .getJSONObject("location").get("lng");
    }

    public ArrayList<String> getDirections(String origin, String destination) throws Exception {
        ArrayList<String> directions = new ArrayList<String>();
        destination = destination.replace(" ", "+");
        origin = origin.replace(" ","+");

        response = client.execute(new HttpGet(googleAPIURL
                + "/directions/json?origin=" + origin + "&destination="
                + destination + "&key=" + KEY));

        entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject jsonStringResult = new JSONObject(responseString);
        JSONArray steps = jsonStringResult.getJSONArray("routes").getJSONObject(0).getJSONArray("legs")
                .getJSONObject(0).getJSONArray("steps");

        for (int i = 0; i < steps.length(); i++) {
            String step = steps.getJSONObject(i).get("html_instructions").toString();
            directions.add(step);
        }

        return directions;
    }

    public ArrayList<Place> generatePlaces() throws Exception{
        response = client.execute(new HttpGet(googleAPIURL
                + "/place/radarsearch/json?location=" + this.latitude
                + "," + this.longitude + "&radius=" + Double.toString(this.radius)
                + "&keyword=" + this.keyword + "&minprice=" + this.minPrice
                + "&sensor=false&key=" + KEY));

        entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject jsonStringResult = new JSONObject(responseString);
        JSONArray places = jsonStringResult.getJSONArray("results");
        return generatePlaceList(places);
    }

    private ArrayList<Place> generatePlaceList(JSONArray results)throws Exception {
        ArrayList<Place> placeResults = new ArrayList<Place>();

        for(int i = 0; i < results.length(); i++) {
            JSONObject place = results.getJSONObject(i);
            try {
                JSONObject placeDetails =
                        getPlaceDetails(place.getString("reference"));

                //Open Time
                String openTime = ((placeDetails.has("opening_hours"))?
                        placeDetails.getJSONObject("opening_hours")
                        .getJSONArray("periods").getJSONObject(this.day).
                                getJSONObject("open").get("time").toString():"0");
                //Close Time
                String closeTime = ((placeDetails.has("opening_hours"))?
                        placeDetails.getJSONObject("opening_hours")
                                .getJSONArray("periods").getJSONObject(this.day).
                                getJSONObject("close").get("time").toString():"2359");
                //Rating
                String placeRatting = (placeDetails.has("rating"))
                        ? placeDetails.get("rating").toString()
                        : "5.0";

                //Check for constraints
                if(!isOpen(openTime,closeTime) || !hasMinRatting(placeRatting)) continue;

                Place singlePlace = new Place();

                singlePlace.setReference(place.getString("reference"));

                singlePlace.setLatitude(placeDetails.getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString());

                singlePlace.setLongitude(placeDetails.getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString());


                singlePlace.setRating(placeRatting);

                if(null != openTime) {
                    singlePlace.setOpenTime(Integer.parseInt(openTime));
                }
                if(null != closeTime) {
                    singlePlace.setCloseTime(Integer.parseInt(closeTime));
                }
                //Reviews
                JSONArray reviewArray = ((placeDetails.has("reviews"))?
                        placeDetails.getJSONArray("reviews"):new JSONArray());

                if(reviewArray.length() > 0) {
                    ArrayList<String> reviews = new ArrayList<String>();
                    for(int x = 0; x < reviewArray.length(); x++) {
                        reviews.add(reviewArray.getJSONObject(x).get("text").toString());
                    }
                    singlePlace.setReviews(reviews);
                }

                //Photos
                ArrayList<String> photos = new ArrayList<String>();
                if (placeDetails.has("photos")) {
                    JSONArray photoReferenceArray =  placeDetails.getJSONArray("photos");
                    for(int x = 0; x < photoReferenceArray.length(); x++) {
                        String photoReference
                                = photoReferenceArray.getJSONObject(x).get("photo_reference").toString();
                        if(!photoReference.equals("")) {
                            photos.add("https://maps.googleapis.com/maps/api/place/photo?"
                                    + "maxwidth=400&photoreference=" + photoReference + "&key=" + KEY);
                        } else {
                            photos.add("http://img4.wikia.nocookie.net/__cb20140530133130" +
                                    "/outlast/images/6/60/No_Image_Available.png");
                        }
                    }
                } else {
                    photos.add("http://img4.wikia.nocookie.net/__cb20140530133130" +
                            "/outlast/images/6/60/No_Image_Available.png");
                }
                singlePlace.setImageURL(photos);

                //Phone Number
                String phoneNumber = ((placeDetails.has("formatted_phone_number"))?
                        placeDetails.get("formatted_phone_number") .toString():"Not Available");
                singlePlace.setPhoneNumber(phoneNumber);

                // Website
                String website = ((placeDetails.has("website"))?
                        placeDetails.get("website").toString():"");
                singlePlace.setWebsite(website);

                //Address
                singlePlace.setAddress(placeDetails.get("formatted_address")
                        .toString());
                //Name
                singlePlace.setName(placeDetails.get("name").toString());

                ArrayList<String> directions = new ArrayList<String>();
                directions = getDirections(this.address, singlePlace.getAddress());
                singlePlace.setDirections(directions);

                placeResults.add(singlePlace);

            } catch (JSONException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }

        }
        return placeResults;
    }

    private JSONObject getPlaceDetails(String reference) throws Exception{
        response = client.execute(new HttpGet(googleAPIURL
                + "/place/details/json?reference=" + reference
                + "&sensor=false&key=" + KEY));
        entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(responseString).getJSONObject("result");
    }

    public String getLatitude() {
        return this.latitude.toString();
    }

    public String getLongitude() {
        return this.longitude.toString();
    }

    private boolean isOpen(String openTime, String closeTime) {
        double open = (null != openTime && !openTime.equals(""))
                ? Double.parseDouble(openTime) : 0;
        double close = (null != closeTime && !closeTime.equals(""))
                ? Double.parseDouble(closeTime) : 2359;
        return (this.startHour >= open || this.endHour <= close);
    }

    private boolean hasMinRatting(String placeRatting) {
        double ratting = (null != placeRatting && placeRatting.equals(""))
                ? Double.parseDouble(placeRatting) : 5.0;
        return ratting >= this.minRating;
    }
}
