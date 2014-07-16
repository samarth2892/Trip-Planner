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
        <%
        ArrayList<String> directions = (ArrayList<String>) request.getSession().getAttribute("directions");
        for (String route : directions) {%>
            <%=route%><br/>
        <%}%>
</body>
</html>