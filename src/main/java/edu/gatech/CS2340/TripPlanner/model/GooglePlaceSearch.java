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
    private String minRating = "";
    private int radius = 50000;
    private int startHour = 0;
    private int endHour = 2400;

    private Object latitude;
    private Object longitude;
    private HttpClient client = HttpClientBuilder.create().build();
    private HttpResponse response;
    private HttpEntity entity;
    private ArrayList<Place> placeResults;

    public GooglePlaceSearch(String address, String keyword, String minPrice, String minRating) {
        if (!(null == address || address.equals(""))) this.address = address;
        if (!(null == keyword || keyword.equals(""))) this.keyword = keyword;
        this.minPrice = minPrice;
        this.minRating = minRating;
    }

    /*TODO
    public void setMinRating(int minRating) {
        if(minRating > 0 && minRating < 5) {
            this.minRating = minRating;
        }
    }*/

    public void setRadius(int radius) {
        if (radius > 0 && radius < 50000) {
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
                + "," + this.longitude + "&radius=" + Integer.toString(radius)
                + "&keyword=" + this.keyword + "&minprice=" + this.minPrice
                + "&rating=" + this.minRating + "&sensor=false&key=" + KEY));

        entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject jsonStringResult = new JSONObject(responseString);
        JSONArray places = jsonStringResult.getJSONArray("results");
        return generatePlaceList(places);
    }

    private ArrayList<Place> generatePlaceList(JSONArray results)throws Exception{

        placeResults = new ArrayList<Place>();

        for(int i = 0; i < results.length(); i++) {
            JSONObject place = results.getJSONObject(i);

            JSONObject placeDetails =
            getPlaceDetails(place.getString("reference"));

            Place singlePlace = new Place();
            singlePlace.setReference(place.getString("reference"));
            singlePlace.setAddress(placeDetails.get("formatted_address").toString());
            placeResults.add(singlePlace);

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
}
