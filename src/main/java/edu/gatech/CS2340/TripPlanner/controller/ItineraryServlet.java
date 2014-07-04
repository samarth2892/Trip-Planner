package main.java.edu.gatech.CS2340.TripPlanner.controller;


import main.java.edu.gatech.CS2340.TripPlanner.model.Place;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        urlPatterns = {"/Account/addToItinerary"}
)

public class ItineraryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        int placeId = Integer.parseInt(request.getParameter("id"));

        ArrayList<Place> sessionItinerary
                = (ArrayList<Place>)request.getSession().getAttribute("itineraryPlaces");


        ArrayList<Place> places
                = (ArrayList<Place>) request.getSession().getAttribute("placeResult");

        sessionItinerary.add(places.get(placeId));

        request.getSession().setAttribute("itineraryPlaces", sessionItinerary);


        response.getWriter().print(sessionItinerary.size());


    }


}
