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

public class GooglePlaceSearch {
    final static String KEY = "AIzaSyAekNru_w4ZwcjbMfMXwVK-TnFLtj4TQUM";

    private int minprice = 0;
    private String address = "";
    private String keyword = "";
    private Object latitude;
    private Object longitude;
    private JSONArray places;
    private HttpClient client = HttpClientBuilder.create().build();
    private HttpResponse response;
    private HttpEntity entity;

    public GooglePlaceSearch(String address, String keyword, int minprice) {
        this.address = address;
        this.keyword = keyword;
        this.minprice = minprice;
    }

    public void search() throws MalformedURLException {
        try {
            generateGeocode();
            generatePlaces();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateGeocode() throws Exception {
        response = client.execute(new HttpGet("https://maps.googleapis.com/maps/api/geocode/json"
                + "?address=" + this.address + "&key=" + KEY));
        entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        JSONObject j = new JSONObject(responseString);
        JSONArray locationDetails = j.getJSONArray("results");
        JSONObject location = locationDetails.getJSONObject(0);

        this.latitude = location.getJSONObject("geometry").getJSONObject("location").get("lat");
        this.longitude = location.getJSONObject("geometry").getJSONObject("location").get("lng");
    }

    public void generatePlaces() throws Exception{

        StringBuilder searchURL = new StringBuilder(
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                + "?location=" + this.latitude + "," + this.longitude
                + "&radius=50000&keyword=" + this.keyword + "&minprice="
                        + this.minprice + "&sensor=false&key=" + KEY);
        response = client.execute(new HttpGet(searchURL.toString()));
        entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject j = new JSONObject(responseString);
        places = j.getJSONArray("results");

        for(int i = 0; i < places.length(); i++) {
            JSONObject place = places.getJSONObject(i);
            System.out.println(place.getString("name"));
        }
    }
}
