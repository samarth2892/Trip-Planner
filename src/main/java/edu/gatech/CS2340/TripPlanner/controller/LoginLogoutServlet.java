package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.AccountDb;
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


@WebServlet(
        urlPatterns = {
        "/LogOut",
        "/AccountLogIn" }
)

public class LoginLogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
        throws IOException, ServletException {

        AccountDb database = new AccountDb();
        database.connect();

        RequestDispatcher dispatcher;

        String error;
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (database.login(username, password)) {
            request.getSession().setAttribute("userStatus", username);

            Itinerary sessionItinerary = new Itinerary();
            HashMap<String, Place> itineraryPlaces = new HashMap<String, Place>();
            sessionItinerary.setMap(itineraryPlaces);
            request.getSession().setAttribute("sessionItinerary", sessionItinerary);

            PlaceDb places = new PlaceDb();
            places.connect();

            ArrayList<Itinerary> savedSessionItineraries = places.loadAllItineraries(username);
            request.getSession().setAttribute("savedSessionItineraries", savedSessionItineraries);

            response.sendRedirect("http://localhost:8080/TripPlanner/Account/home.jsp");
            return;
        } else {
            error = "Username or password incorrect";
            request.setAttribute("error", error);
            dispatcher = request.getRequestDispatcher("logIn.jsp");
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
        throws IOException, ServletException {

        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
