package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.GooglePlaceSearch;
import main.java.edu.gatech.CS2340.TripPlanner.model.Itinerary;
import main.java.edu.gatech.CS2340.TripPlanner.model.Place;
import main.java.edu.gatech.CS2340.TripPlanner.model.PlaceDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = {
        "/Account/finalizeItinerary",
        "/Account/showSavedItinerary"
})

public class finalizeItineraryServlet extends HttpServlet {

    private  PlaceDb places = new PlaceDb();
    private ArrayList<String> directions = new ArrayList<String>();
    private ArrayList<String> directionSteps = new ArrayList<String>();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher
                = request.getRequestDispatcher("itinerary.jsp");

        places.connect();

        String username = request.getSession().getAttribute("userStatus").toString();

        Itinerary sessionItinerary
                = (Itinerary) request.getSession().getAttribute("sessionItinerary");

        HashMap<String, Place> itineraryPlaces = sessionItinerary.getMap();
        if(request.getParameter("directionsButton") != null
                && request.getParameter("directionsButton").equals("Get Directions")) {

            GooglePlaceSearch search = new GooglePlaceSearch("",0);

            if(request.getParameter("transportation").equals("bus")) {
                try {
                    Itinerary itinerary = places.loadItinerary(1, request.getSession().getAttribute("userStatus").toString()); //TODO get the correct itin id

                    String origin = itinerary.getOrigin();
                    origin = origin.replace(" ", "+");
                    String previousDestination = "";

                    HashMap<String, Place> itineraryMap = itinerary.getMap();

                    for (String key : itineraryMap.keySet()) {
                        String destination = itineraryMap.get(key).getAddress();
                        destination = destination.replace(" ", "+");
                        if (key.equals("1")) {
                            directionSteps = search.getBusDirections(origin, destination);
                            for (String step : directionSteps) {
                                directions.add(step);
                            }
                        } else {
                            directionSteps = search.getBusDirections(previousDestination, destination);
                            for (String step : directionSteps) {
                                directions.add(step);
                            }
                        }
                        if (Integer.parseInt(key) == itineraryMap.size()) {
                            directionSteps = search.getBusDirections(destination, origin);
                            for (String step : directionSteps) {
                                directions.add(step);
                            }
                        }
                        previousDestination = destination;
                    }
                } catch (Exception e) {
                        e.printStackTrace();
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    Itinerary itinerary = places.loadItinerary(1,
                            request.getSession().getAttribute("userStatus").toString());

                    String origin = itinerary.getOrigin();
                    origin = origin.replace(" ", "+");

                    stringBuilder.append("origin=" + origin + "&destination=" + origin
                        + "&waypoints=optimize:true%7C");

                    HashMap<String, Place> itineraryMap = itinerary.getMap();

                    for (String key : itineraryMap.keySet()) {
                        String waypoint = itineraryMap.get(key).getAddress();
                        waypoint = waypoint.replace(" ", "+");
                        stringBuilder.append(waypoint);
                        if (Integer.parseInt(key) != itineraryMap.size()) {
                            stringBuilder.append("%7C");
                        }
                    }

                    directionSteps = search.getDirections(stringBuilder.toString(),
                            request.getParameter("transportation").toString());

                    for (String step : directionSteps) {
                        directions.add(step);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            request.getSession().setAttribute("directions", directions);

        } else if(request.getAttribute("orderedPlaces") != null) {

            Place[] orderedPlaces = (Place[]) request.getAttribute("orderedPlaces");
            sessionItinerary.setOrderedPlacesArray(orderedPlaces);

            if (sessionItinerary.getOrigin() == null) {
                sessionItinerary.setOrigin(request.getSession().getAttribute("sessionStartAddress").toString());
            }

            if (sessionItinerary.getDate() == null) {
                sessionItinerary.setDate(request.getSession().getAttribute("sessionDate").toString());
            }

            places.addItinerary(sessionItinerary, username);

        } else if(sessionItinerary.getId() != 0) {
                places.clearItinerary(username, sessionItinerary.getId());
                places.updateAllItineraryIds(sessionItinerary.getId(), username);
        }

        sessionItinerary = new Itinerary();
        sessionItinerary.setMap(new HashMap<String, Place>());
        request.getSession().setAttribute("sessionItinerary", sessionItinerary);

        ArrayList<Itinerary> savedSessionItineraries = places.loadAllItineraries(username);
        request.getSession().setAttribute("savedSessionItineraries", savedSessionItineraries);

        dispatcher.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher
                = request.getRequestDispatcher("itinerary.jsp");

        places.connect();

        String username = request.getSession().getAttribute("userStatus").toString();

        ArrayList<Itinerary> savedSessionItineraries = places.loadAllItineraries(username);

        request.getSession().setAttribute("savedSessionItineraries", savedSessionItineraries);

        int id = Integer.parseInt(request.getParameter("savedItineraryId"));

        Itinerary sessionItinerary = savedSessionItineraries.get(id);

        request.getSession().setAttribute("sessionDate", sessionItinerary.getDate());

        request.getSession().setAttribute("sessionItinerary", sessionItinerary);

        dispatcher.forward(request, response);
    }
}