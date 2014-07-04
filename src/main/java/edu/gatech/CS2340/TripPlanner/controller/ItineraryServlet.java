package main.java.edu.gatech.CS2340.TripPlanner.controller;


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

public class ItineraryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {


        HashMap<String, Place> sessionItinerary
                = (HashMap<String, Place>) request.getSession().getAttribute("itineraryPlaces");


        if (!request.getParameter("addId").equals("")) {
            ArrayList<Place> places
                    = (ArrayList<Place>) request.getSession().getAttribute("sessionPlaceResult");

            String placeId = request.getParameter("addId");

            sessionItinerary.put(placeId, places.get(Integer.parseInt(placeId)));

            request.getSession().setAttribute("itineraryPlaces", sessionItinerary);

        } else if (!request.getParameter("removeId").equals("")) {

            System.out.println("before removeId");

            String placeId = request.getParameter("removeId");

            sessionItinerary.remove(placeId);

            request.getSession().setAttribute("itineraryPlaces", sessionItinerary);

        }
        response.getWriter().print(sessionItinerary.size());

    }
}
