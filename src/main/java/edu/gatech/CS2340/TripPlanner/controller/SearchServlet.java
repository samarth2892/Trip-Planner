package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.GooglePlaceSearch;
import main.java.edu.gatech.CS2340.TripPlanner.model.Place;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {
        "/Account/Search"
})

public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/Account/home.jsp");

        String address = request.getParameter("address");

        String day = request.getParameter("day");

        String keyword = request.getParameter("keyword");

        String minPrice = request.getParameter("minPrice");

        Double minRating = (null != request.getParameter("minRating")
                && !request.getParameter("minRating").equals(""))
                ? Double.parseDouble(request.getParameter("minRating"))
                : 1.0;

        int radius = (null != request.getParameter("maxDistance")
                && !request.getParameter("maxDistance").equals(""))
                ? Integer.parseInt(request.getParameter("maxDistance"))
                : 5000;

        double startHour = (null != request.getParameter("startHour") &&
                !request.getParameter("startHour").equals(""))
                ? Double.parseDouble(request.getParameter("startHour"))
                : 0 ;
        String startAMPM = request.getParameter("startAMPM");

        double endHour = (null != request.getParameter("endHour") &&
                    !request.getParameter("endHour").equals(""))
                ? Double.parseDouble(request.getParameter("endHour"))
                : 2359 ;
        String endAMPM = request.getParameter("endAMPM");

        if (null != startAMPM && startAMPM.equals("pm") && startHour != 0) {
            startHour += 1200;
        }
        if (null != endAMPM && endAMPM.equals("pm") && endHour != 2359) {
            endHour += 1200;
        }

        address = address.replaceAll(" ", "+");
        GooglePlaceSearch search = new GooglePlaceSearch(address,
                Integer.parseInt(day));
        search.setStartEndHour(startHour, endHour);
        search.setKeyword(keyword);
        search.setMinPrice(minPrice);
        search.setMinRating(minRating);
        search.setRadiusInMeters(radius * 1609.34);

        ArrayList<Place> placeResult = search.search();
        request.setAttribute("center",search.getLatitude()
                + "," + search.getLongitude());

        request.setAttribute("placeResult", placeResult);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }

}
