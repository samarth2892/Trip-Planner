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
    private String minPrice = "";
    private String minRating;
    private double radius = 0.0;
    private int startHour = 0;
    private int endHour = 2359;

    private Object latitude;
    private Object longitude;
    private HttpClient client = HttpClientBuilder.create().build();
    private HttpResponse response;
    private HttpEntity entity;
    private ArrayList<Place> placeResults;

    public GooglePlaceSearch(String address, String keyword, String minPrice,
                             String minRating, String maxDistance,
                             int startHour, int endHour) {
        if (!(null == address || address.equals(""))) this.address = address;
        if (!(null == keyword || keyword.equals(""))) this.keyword = keyword;
        this.minPrice = minPrice;
        this.minRating = minRating;
        this.setRadius(Double.parseDouble(maxDistance) / 3.28 * 5280.0);
        this.setStartEndHour(startHour, endHour);
    }

    /*TODO
    public void setMinRating(int minRating) {
        if(minRating > 0 && minRating < 5) {
            this.minRating = minRating;
        }
    }*/

    public void setRadius(double radius) {
        if (radius > 0.0 && radius < 50000.0) {
            this.radius = radius;
        }
    }

    public void setStartEndHour(int startHour, int endHour) {
        if (startHour > 0 && endHour < 2400) {
            this.startHour = startHour;
            this.endHour = endHour;
        }
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

    public ArrayList<Place> generatePlaces() throws Exception{
        response = client.execute(new HttpGet(googleAPIURL
                + "/place/nearbysearch/json?location=" + this.latitude
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

        placeResults = new ArrayList<Place>();

        for(int i = 0; i < results.length(); i++) {
            JSONObject place = results.getJSONObject(i);
            try {
                JSONObject placeDetails =
                        getPlaceDetails(place.getString("reference"));

                Place singlePlace = new Place();
                singlePlace.setReference(place.getString("reference"));
                response = client.execute(new HttpGet(googleAPIURL
                        + "/place/details/json?reference="
                        + singlePlace.getReference() + "&key=" + KEY));

                entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");

                JSONObject jsonStringResult = new JSONObject(responseString);

                JSONObject currentPlace = jsonStringResult
                        .getJSONObject("result");

                singlePlace.setLatitude(placeDetails.getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString());
                singlePlace.setLongitude(placeDetails.getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString());
                String openTime = currentPlace.getJSONObject("opening_hours")
                        .getJSONArray("periods").getJSONObject(0).
                                getJSONObject("open").get("time").toString();
                String closeTime = currentPlace.getJSONObject("opening_hours")
                        .getJSONArray("periods").getJSONObject(0).
                                getJSONObject("close").get("time").toString();

                JSONObject firstReview = currentPlace
                        .getJSONArray("reviews").getJSONObject(0);
                String photoReference = currentPlace.getJSONArray("photos")
                        .getJSONObject(0).get("photo_reference").toString();

                String phoneNumber = currentPlace.get("formatted_phone_number").toString();
                String website = currentPlace.get("website").toString();

                singlePlace.setOpenTime(Integer.parseInt(openTime));
                singlePlace.setCloseTime(Integer.parseInt(closeTime));
                singlePlace.setAddress(placeDetails.get("formatted_address")
                        .toString());
                singlePlace.setName(placeDetails.get("name").toString());
                singlePlace.setRating(placeDetails.get("rating").toString());
                singlePlace.setPhoneNumber(phoneNumber);
                singlePlace.setWebsite(website);
                singlePlace.setImageURL("https://maps.googleapis.com/maps/api/place/photo?"
                        + "maxwidth=400&photoreference=" + photoReference + "&key=" + KEY);
                
                /*if (Double.parseDouble(this.minRating)
                        <= Double.parseDouble(singlePlace.getRating())
                        && this.startHour >= singlePlace.getOpenTime()
                        && this.endHour <= singlePlace.getCloseTime()) {
                    placeResults.add(singlePlace);
                }*/
                placeResults.add(singlePlace);
            } catch (JSONException e) {
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
}
