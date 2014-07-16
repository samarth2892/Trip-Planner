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
            request.setAttribute("directions", "test");

            Place[] orderedPlaces = (Place[]) request.getAttribute("orderedPlaces");

            GooglePlaceSearch search = new GooglePlaceSearch("",0);

            try {
                ArrayList<String> directions
                    = search.getDirections(sessionItinerary.getOrigin(), orderedPlaces[1].getAddress());
                System.out.println(directions.get(0));

            } catch (Exception e) {
                e.printStackTrace();
            }

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