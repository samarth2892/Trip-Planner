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
import java.util.ArrayList;

@WebServlet(urlPatterns = {
        "/Account/AddPlace"
        })

public class AddPlaceServlet extends HttpServlet {

    @Override
    protected final void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
                                  throws IOException, ServletException {
        ArrayList<Place> places = (ArrayList<Place>) request.
                getSession().getAttribute("placeResult");
        int index = Integer.parseInt(request.getParameter("index"));
        RequestDispatcher dispatcher;
        String addConfirmation;
        //AccountDb accountDatabase = new AccountDb();
        PlaceDb placeDatabase = new PlaceDb();
        placeDatabase.connect();
        //int userId = accountDatabase.getUserId();

        Place place = new Place();
        place.setReference(places.get(index).getReference());
        place.setName(places.get(index).getName());
        place.setAddress(places.get(index).getAddress());
        place.setPhoneNumber(places.get(index).getPhoneNumber());
        place.setOpenTime(places.get(index).getOpenTime());
        place.setCloseTime(places.get(index).getCloseTime());

        /*fif (!placeDatabase.placeExists(place)) {
            placeDatabase.addPlace(place, "0");
            addConfirmation = place.getName()
            + " has been added to your itinerary.";
            dispatcher = request.getRequestDispatcher("home.jsp");
        } else {
            addConfirmation = place.getName()
            + " is already in your itinerary.";
            request.setAttribute("errorCount", Integer.toString(2));
            dispatcher = request.getRequestDispatcher("home.jsp");
        }
        request.setAttribute("error", addConfirmation);
        dispatcher.forward(request, response);*/
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
        throws IOException, ServletException {

    }
}
