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

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/Account/home.jsp");

        StringBuffer error = new StringBuffer("");
        int errorCount = 0;

        String address = request.getParameter("address");
        String keyword = request.getParameter("search");
        int minprice = 0; //todo

        address = address.replaceAll(" ", "+");
        GooglePlaceSearch search = new GooglePlaceSearch(address, keyword, minprice);
        search.search();
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }

}
