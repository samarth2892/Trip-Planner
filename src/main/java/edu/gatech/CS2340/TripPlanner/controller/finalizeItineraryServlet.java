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

    private PlaceDb places = new PlaceDb();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
        throws IOException, ServletException {

        RequestDispatcher dispatcher
            = request.getRequestDispatcher("itinerary.jsp");
        String username = request.getSession()
                .getAttribute("userStatus").toString();
        Itinerary sessionItinerary
            = (Itinerary) request
                .getSession().getAttribute("sessionItinerary");
        places.connect();

        Place[] orderedPlaces = (Place[]) request.getAttribute("orderedPlaces");
        sessionItinerary.setOrderedPlacesArray(orderedPlaces);

        if (request.getParameter("directionsButton") != null
                && request.getParameter("directionsButton").
                    equals("Get Directions")) {

            GooglePlaceSearch search = new GooglePlaceSearch("", 0);
            ArrayList<String> directions = new ArrayList<String>();


            if (request.getParameter("transportation").equals("bus")) {
                directions = getDirectionsByBus(sessionItinerary);
            } else {
                try {
                    directions = search.getDirections(sessionItinerary,
                            request.getParameter("transportation"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            request.setAttribute("directionsStatus", "OK");
            request.getSession().setAttribute("directions", directions);

        } else if (request.getAttribute("orderedPlaces") != null) {

            if (sessionItinerary.getOrigin() == null) {
                sessionItinerary.setOrigin(request.getSession().
                        getAttribute("sessionStartAddress").toString());
            }

            if (sessionItinerary.getDate() == null) {
                sessionItinerary.setDate(request.getSession().
                        getAttribute("sessionDate").toString());
            }

            places.addItinerary(sessionItinerary, username);

        } else if (sessionItinerary.getId() != 0) {
            places.clearItinerary(username, sessionItinerary.getId());
            places.updateAllItineraryIds(sessionItinerary.getId(),
                        username);
        }

        if (!(request.getParameter("directionsButton") != null
                && request.getParameter("directionsButton").
                    equals("Get Directions"))) {
            sessionItinerary = new Itinerary();
            sessionItinerary.setMap(new HashMap<String, Place>());
            request.getSession().setAttribute("sessionItinerary",
                    sessionItinerary);
        }

        ArrayList<Itinerary> savedSessionItineraries
            = places.loadAllItineraries(username);
        request.getSession().setAttribute("savedSessionItineraries",
                savedSessionItineraries);

        dispatcher.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
        throws IOException, ServletException {

        RequestDispatcher dispatcher
            = request.getRequestDispatcher("itinerary.jsp");

        places.connect();

        String username = request.getSession().
                getAttribute("userStatus").toString();
        ArrayList<Itinerary> savedSessionItineraries
            = places.loadAllItineraries(username);

        request.getSession().setAttribute("savedSessionItineraries",
                savedSessionItineraries);

        int id = Integer.parseInt(request.getParameter("savedItineraryId"));

        Itinerary sessionItinerary = savedSessionItineraries.get(id);

        request.getSession().setAttribute("sessionDate",
                sessionItinerary.getDate());

        request.getSession().setAttribute("sessionItinerary",
                sessionItinerary);

        dispatcher.forward(request, response);
    }

    private ArrayList<String> getDirectionsByBus(Itinerary sessionItinerary) {
        String origin;
        String destination;
        Place[] orderedPlaces = sessionItinerary.getOrderedPlacesArray();
        GooglePlaceSearch search = new GooglePlaceSearch("", 0);
        ArrayList<String> directions = new ArrayList<String>();
        directions.add("<h3><span id='startEndAddress'>Directions</span></h3>");
        ArrayList<String> steps = new ArrayList<String>();

        int i = 0;
        while (i < orderedPlaces.length) {
            if (i == 0) {
                directions.add("<h3>From: "
                        + "<span id='startEndAddress'>"
                        + sessionItinerary.getOrigin() + "</span><br/>"
                        + " To: " + orderedPlaces[i].getName() + "<br/>"
                        + "<span id='startEndAddress'>"
                        + orderedPlaces[i].getAddress() + "</span></h3>");
                origin = sessionItinerary.getOrigin();
                destination = orderedPlaces[i].getAddress();
            } else {
                directions.add("<h3>From: "
                        + orderedPlaces[i - 1].getName() + "<br/>"
                        + "<span id='startEndAddress'>"
                        + orderedPlaces[i - 1].getAddress() + "</span><br/>"
                        + " To: " + orderedPlaces[i].getName() + "<br/>"
                        + "<span id='startEndAddress'>"
                        + orderedPlaces[i].getAddress() + "</span></h3>");
                origin = orderedPlaces[i - 1].getAddress();
                destination = orderedPlaces[i].getAddress();
            }

            try {
                steps = search.getBusDirections(origin.replace(" ", "+"),
                        destination.replace(" ", "+"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int j = 0; j < steps.size(); j++) {
                directions.add((j + 1) + ". " + steps.get(j));
            }
            i++;
        }
        return directions;
    }
}