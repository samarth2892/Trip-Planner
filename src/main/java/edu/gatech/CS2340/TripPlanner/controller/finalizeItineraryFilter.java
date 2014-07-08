package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.Place;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(
        filterName = "finalizeItineraryFilter",
        urlPatterns = {"/Account/finalizeItinerary"}
)
public class finalizeItineraryFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("itinerary.jsp");

        HashMap<String, Place> sessionItinerary
                = (HashMap<String, Place>) request.getSession().getAttribute("itineraryPlaces");

        //TODO this where we will check if the user entered the right order and mode of transportation
        for(Map.Entry<String, Place> entry : sessionItinerary.entrySet()) {
            System.out.println(request.getParameter(entry.getKey() + "-order"));
        }
        dispatcher.forward(request, response);
        return;

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
