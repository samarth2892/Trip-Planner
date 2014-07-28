package main.java.edu.gatech.CS2340.TripPlanner.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.edu.gatech.CS2340.TripPlanner.model.Itinerary;
import main.java.edu.gatech.CS2340.TripPlanner.model.Place;

@WebFilter(
        filterName = "finalizeItineraryFilter",
        urlPatterns = {"/Account/finalizeItinerary" }
)
public class finalizeItineraryFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public final void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("itinerary.jsp");

        Itinerary sessionItinerary
            = (Itinerary) request.getSession().
                getAttribute("sessionItinerary");
        HashMap<String, Place> itineraryPlaces
            = sessionItinerary.getMap();

        if (!itineraryPlaces.isEmpty()) {
            Place[] orderedPlaces = new Place[itineraryPlaces.size()];

            if (null == request.getParameter("transportation")) {
                request.setAttribute("error", "Please"
                        + " select a mode of transportation");
                dispatcher.forward(request, response);
                return;
            } else {
                for (Map.Entry<String,
                        Place> entry : itineraryPlaces.entrySet()) {
                    int order
                        = (!request.getParameter(entry.getKey()
                            + "-order").equals(""))
                            ? Integer.parseInt(request.
                                    getParameter(entry.getKey() + "-order"))
                            : -1;
                    if (order != -1 && orderedPlaces[order - 1] == null) {
                        orderedPlaces[order - 1] = entry.getValue();
                    } else {
                        request.setAttribute("error",
                                "Please select a valid order of visit");
                        dispatcher.forward(request, response);
                        return;
                    }
                }
            }

            request.setAttribute("orderedPlaces", orderedPlaces);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
