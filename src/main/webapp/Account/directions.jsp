<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.PlaceDb" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Itinerary" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Place" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.GooglePlaceSearch" %>
<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Directions</title>
</head>
<body>
        <%PlaceDb places = new PlaceDb();
        places.connect();

        Itinerary itinerary = places.loadItinerary(1, request.getSession().getAttribute("userStatus").toString()); //TODO get the correct itin id

        String origin = itinerary.getOrigin();
        origin = origin.replace(" ", "+");
        String previousDestination = "";
        String previousKey = "";

        GooglePlaceSearch search = new GooglePlaceSearch(origin, 0); //TODO put the correct day

        HashMap<String, Place> itineraryMap = itinerary.getMap();

        for (String key : itineraryMap.keySet()) {
            String destination = itineraryMap.get(key).getAddress();
            destination = destination.replace(" ", "+");
            if (key.equals("1")) {%>
                Directions from <%=itinerary.getOrigin()%> to <%=itineraryMap.get(key).getName()%><br/>

                <%ArrayList<String> directions = search.getDirections(origin, destination);

                for (int i = 0; i < directions.size(); i++) {%>
                    <br/><%=directions.get(i)%>
                <%}%>
            <%} else {%>
                <br/>Directions from <%=itineraryMap.get(previousKey).getName()%>
                to <%=itineraryMap.get(key).getName()%><br/>

                <%ArrayList<String> directions = search.getDirections(previousDestination, destination);

                for (int i = 0; i < directions.size(); i++) {%>
                    <br/><%=directions.get(i)%>
                <%}%>
            <%}
            if (Integer.parseInt(key) == itineraryMap.size()) {%>
                <br/>Directions from <%=itineraryMap.get(key).getName()%> to <%=itinerary.getOrigin()%><br/>

                <%ArrayList<String> directions = search.getDirections(destination, origin);

                for (int i = 0; i < directions.size(); i++) {%>
                    <br/><%=directions.get(i)%>
                <%}%>
            <%}
            previousDestination = destination;
            previousKey = key;
        }%>
</body>
</html>