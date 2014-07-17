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
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/home.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Fonts/stylesheet.css">
  <link rel="stylesheet" type = "text/css" href="<%=request.getContextPath()%>/stylesheets/print.css"/>
</head>

<body>
<center> <h3> Directions </h3> </center>
    <ol>
        <%
        ArrayList<String> directions = (ArrayList<String>) request.getSession().getAttribute("directions");
        for (String route : directions) {%>
            <li><%=route%></li><br/>
        <%}%>
    </ol>
</body>
</html>