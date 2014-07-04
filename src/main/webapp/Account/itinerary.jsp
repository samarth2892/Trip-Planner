<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Place" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <Title>Home</Title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/home.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Fonts/stylesheet.css">
    <script src="<%=request.getContextPath()%>/JavaScript/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=true_or_false"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/JavaScript/itinerary.js"></script>
    <script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
</head>

<body>
<div id="loadingDiv"></div>

<div id="NavBar">
    <div id="topLeftText"> Welcome  <%= request.getSession().getAttribute("userStatus")%></div>
    <div id="links">
        <span id="homeLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Home</a></span>
        <span id="accLink"><a href="<%=request.getContextPath()%>/Account/settings.jsp">Account Settings</a></span>
        <span id="itineraryLink"><a href="#">Itinerary<span id="noOfPlaces"></span></a></span>
        <span id="logoutLink"><a href="<%=request.getContextPath()%>/LogOut">LogOut</a></span>
    </div>
</div>

<%HashMap<String, Place> sessionItinerary
= (HashMap<String, Place>) request.getSession().getAttribute("itineraryPlaces");%>
<div id="currentItinerary">
    <h2>Current Itinerary</h2>
    <% for(Map.Entry<String, Place> entry : sessionItinerary.entrySet()) {%>
        <div class="itineraryPlace" id="<%=entry.getValue()%>">
            <span class="helper"></span><img src="<%=entry.getValue().getImageURL().get(0)%>" />
            <div id="itineraryPlaceDetails">
                <h4><%=entry.getValue().getName()%></h4>
                    <p> Address: <%=entry.getValue().getAddress()%><br/>
                        Rating: <%=entry.getValue().getRating()%><br/>
                        <button type="button">Remove from Itinerary</button>
                    </p>
            </div>
        </div>
    <%}%>
</div>

<div id="savedItineraries" >
    <h3>Saved Itineraries</h3>

</div>

</body>
</html>