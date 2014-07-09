package main.java.edu.gatech.CS2340.TripPlanner.controller;

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
        "/Account/finalizeItinerary"
})

public class finalizeItineraryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher
                = request.getRequestDispatcher("itinerary.jsp");

        PlaceDb places = new PlaceDb();
        places.connect();

        Itinerary sessionItinerary
                = (Itinerary) request.getSession().getAttribute("sessionItinerary");
        HashMap<String, Place> itineraryPlaces
                = sessionItinerary.getMap();

        Place[] orderedPlaces = (Place[]) request.getAttribute("orderedPlaces");

        String username = request.getSession().getAttribute("userStatus").toString();
        sessionItinerary.setOrderedPlacesArray(orderedPlaces);
        sessionItinerary.setOrigin(request.getSession().getAttribute("sessionStartAddress").toString());
        sessionItinerary.setDate(request.getSession().getAttribute("sessionDate").toString());


        places.addItinerary(sessionItinerary, username);

        itineraryPlaces.clear();
        sessionItinerary.setMap(itineraryPlaces);
        request.getSession().setAttribute("sessionItinerary", sessionItinerary);

        ArrayList<Itinerary> savedSessionItineraries = places.loadAllItineraries(username);

        request.getSession().setAttribute("savedSessionItineraries", savedSessionItineraries);

        dispatcher.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}