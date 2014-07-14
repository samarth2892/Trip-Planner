package main.java.edu.gatech.CS2340.TripPlanner.controller;


import main.java.edu.gatech.CS2340.TripPlanner.model.Itinerary;
import main.java.edu.gatech.CS2340.TripPlanner.model.Place;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(
        urlPatterns = {"/Account/changeItinerary"}
)

public class SessionItineraryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {


        Itinerary sessionItinerary
                = (Itinerary) request.getSession().getAttribute("sessionItinerary");
        HashMap<String, Place> itineraryPlaces
                = sessionItinerary.getMap();


        if (!request.getParameter("addId").equals("")) {
            ArrayList<Place> places
                    = (ArrayList<Place>) request.getSession().getAttribute("sessionPlaceResult");

            String placeId = request.getParameter("addId");

            itineraryPlaces.put(placeId, places.get(Integer.parseInt(placeId)));
            sessionItinerary.setMap(itineraryPlaces);
            request.getSession().setAttribute("sessionItinerary", sessionItinerary);

        } else if (!request.getParameter("removeId").equals("")) {

            String placeId = request.getParameter("removeId");

            itineraryPlaces.remove(placeId);

            sessionItinerary.setMap(itineraryPlaces);

            request.getSession().setAttribute("sessionItinerary", sessionItinerary);

        } else if (!request.getParameter("startOver").equals("")) {
            sessionItinerary = new Itinerary();
            sessionItinerary.setMap(new HashMap<String, Place>());
            request.getSession().setAttribute("sessionItinerary", sessionItinerary);
        }
        response.getWriter().print(itineraryPlaces.size());

    }
}
