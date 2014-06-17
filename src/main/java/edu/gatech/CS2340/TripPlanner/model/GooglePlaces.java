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

public class GooglePlaces {

    public GooglePlaces (String lat, String lon, String key) throws MalformedURLException {
        StringBuilder searchURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                + "?location=" + lat + "," + lon
                + "&radius=50000&keyword=food&sensor=false&key=" + key);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response;
        try{
            response = client.execute(new HttpGet(searchURL.toString()));
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            JSONObject j = new JSONObject(responseString);
            JSONArray places = j.getJSONArray("results");

            for(int i = 0; i < places.length(); i++) {
                JSONObject place = places.getJSONObject(i);
                System.out.println(place.getString("name"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... agrs){
        String key = "AIzaSyAekNru_w4ZwcjbMfMXwVK-TnFLtj4TQUM";
        String lat = "33.989525";
        String lon = "-84.610013";
        try {
            GooglePlaces g = new GooglePlaces(lat, lon, key);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
