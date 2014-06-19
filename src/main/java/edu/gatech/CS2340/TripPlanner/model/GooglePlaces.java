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

import java.util.Scanner;
import java.io.IOException;
import java.net.MalformedURLException;

public class GooglePlaces {

    public GooglePlaces (String address, String city, String state, String key) throws MalformedURLException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response;

        try{
            response = client.execute(new HttpGet("https://maps.googleapis.com/maps/api/geocode/json"
                    + "?address=" + address + ",+" + city + ",+" + state + "&key=" + key));
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            JSONObject j = new JSONObject(responseString);
            JSONArray locationDetails = j.getJSONArray("results");
            JSONObject location = locationDetails.getJSONObject(0);
            Object latitude = location.getJSONObject("geometry").getJSONObject("location").get("lat");
            Object longitude = location.getJSONObject("geometry").getJSONObject("location").get("lng");

            StringBuilder searchURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json"
            + "?location=" + latitude + "," + longitude
            + "&radius=50000&keyword=food&sensor=false&key=" + key);
            response = client.execute(new HttpGet(searchURL.toString()));
            entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");

            j = new JSONObject(responseString);
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
        //String lat = "33.989525";
        //String lon = "-84.610013";
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter address:");
        String address = scan.nextLine();
        System.out.println("Enter city:");
        String city = scan.nextLine();
        System.out.println("Enter two letter state abbreviation");
        String state = scan.nextLine();
        address = address.replaceAll(" ", "+");
        city = city.replaceAll(" ", "+");

        try {
            GooglePlaces g = new GooglePlaces(address, city, state, key);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
