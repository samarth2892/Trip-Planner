package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.Itinerary;
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

        Itinerary sessionItinerary
                = (Itinerary) request.getSession().getAttribute("sessionItinerary");
        HashMap<String, Place> itineraryPlaces
                = sessionItinerary.getMap();

        Place[] orderedPlaces = new Place[itineraryPlaces.size()];

        if (null == request.getParameter("transportation")) {
            request.setAttribute("error","*Please select a mode of transportation");
            dispatcher.forward(request, response);
            return;

        } else {
            for(Map.Entry<String, Place> entry : itineraryPlaces.entrySet()) {
                int order
                        = (!request.getParameter(entry.getKey() + "-order").equals(""))
                        ? Integer.parseInt(request.getParameter(entry.getKey() + "-order"))
                        : -1;
                if (order != -1 && orderedPlaces[order - 1] == null) {
                    orderedPlaces[order - 1] = entry.getValue();
                } else {
                    request.setAttribute("error","Please select a valid order of visit");
                    dispatcher.forward(request, response);
                    return;
                }
            }
        }

        request.setAttribute("orderedPlaces", orderedPlaces);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
