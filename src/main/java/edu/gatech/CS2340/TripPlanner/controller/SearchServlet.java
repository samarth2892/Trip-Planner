package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.GooglePlaceSearch;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {
        "/Account/Search"
})

public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        GooglePlaceSearch search;

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/Account/home.jsp");

        StringBuffer error = new StringBuffer("");
        int errorCount = 0;
        String key = "AIzaSyAekNru_w4ZwcjbMfMXwVK-TnFLtj4TQUM";

        /*String address = request.getParameter("address");
        int openTime = request.getParameter("openTime");
        int closeTime = request.getParameter("closeTime");
        String address = request.getParameter("address");
        int radius = request.getParameter("radius");
        double rating = request.getParameter("rating");

        search = new GooglePlaceSearch(address, key);*/
        String address = request.getParameter("address");
        search = new GooglePlaceSearch(address);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }

}
