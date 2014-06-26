package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.AccountDb;
import main.java.edu.gatech.CS2340.TripPlanner.model.Place;
import main.java.edu.gatech.CS2340.TripPlanner.model.PlaceDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {
        "/AddPlace"
})

public class AddPlaceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher;
        String addConfirmation;
        AccountDb accountDatabase = new AccountDb();
        PlaceDb placeDatabase = new PlaceDb();
        accountDatabase.connect();
        int userId = accountDatabase.getUserId();

        Place place = new Place();
        place.setReference(request.getParameter("reference"));
        place.setName(request.getParameter("name"));
        place.setAddress(request.getParameter("address"));
        place.setPhoneNumber(request.getParameter("phone"));
        place.setOpenTime(request.getIntHeader("openTime"));
        place.setCloseTime(request.getIntHeader("closeTime"));

        if (!placeDatabase.placeExists(place)) {
            placeDatabase.addPlace(place);
            addConfirmation = place.getName() + " has been added to your itinerary.";
            dispatcher = request.getRequestDispatcher("home.jsp");
        } else {
            addConfirmation = place.getName() + " is already in your itinerary.";
            request.setAttribute("errorCount", Integer.toString(2));
            dispatcher = request.getRequestDispatcher("home.jsp");
        }
        request.setAttribute("error", addConfirmation);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}
