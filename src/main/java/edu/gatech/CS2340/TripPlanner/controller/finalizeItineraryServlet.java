package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.Place;
import main.java.edu.gatech.CS2340.TripPlanner.model.PlaceDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        HashMap<String, Place> sessionItinerary
                = (HashMap<String, Place>) request.getSession().getAttribute("itineraryPlaces");

        Place[] orderedPlaces = (Place[]) request.getAttribute("orderedPlaces");


        for (Place orderedPlace : orderedPlaces) {
            System.out.println(orderedPlace.getName());
        }
        dispatcher.forward(request, response);


    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}