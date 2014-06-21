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

        StringBuffer error = new StringBuffer("");
        int errorCount = 0;

        String address = request.getParameter("address");
        String keyword = request.getParameter("search");
        int minPrice = 0; //todo

        address = address.replaceAll(" ", "+");
        GooglePlaceSearch search = new GooglePlaceSearch(address, keyword, minPrice);
        ArrayList<Place> placeResult = search.search();

        request.setAttribute("placeResult", placeResult);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }

}
