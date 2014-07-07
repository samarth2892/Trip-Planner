<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Place" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <Title>Itineraries</Title>
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
    <%if(sessionItinerary.size() > 0) {%>
    <form action="<%=request.getContextPath()%>/Account/finalizeItinerary" method="post">
    <div id="currentItineraryTitle"><b>Current Itinerary</b>
        <select style="margin-left:50px" name="transportation">
            <option value="" disabled selected>Transportation</option>
            <option value="car" ${param.transportation == 'car' ? 'selected' : ''}>Car</option>
            <option value="bike" ${param.transportation == 'bike' ? 'selected' : ''}>Bicycle</option>
            <option value="bus" ${param.transportation == 'bus' ? 'selected' : ''}>Bus</option>
            <option value="walk" ${param.transportation == 'walk' ? 'selected' : ''}>Walk</option>
        </select>
        <button type="submit">Finalize and get directions</button> <button type="button" onclick="javascript:startOver()">Start Over</button>
        <br/></b>
    </div>
    <% for(Map.Entry<String, Place> entry : sessionItinerary.entrySet()) {%>
        <div class="itineraryPlace" id="itineraryPlace-<%=entry.getKey()%>">
            <span class="helper"></span><img src="<%=entry.getValue().getImageURL().get(0)%>" />
            <div id="itineraryPlaceDetails">
                <h4><%=entry.getValue().getName()%> <span style="float: right">Order of visit:
                    <input name="<%=entry.getKey()%>-order" min="1" max="<%=sessionItinerary.size()%>"type="number" style="width:40px"/></span></h4>

                    <p> Address: <%=entry.getValue().getAddress()%><br/>
                        Hours on: <%=request.getSession().getAttribute("date").toString()%>
                        &nbsp;Open: <%=entry.getValue().getOpenTimeString()%>
                        &nbsp;Close: <%=entry.getValue().getCloseTimeString()%><br/>
                        <button type="button" onclick="javascript:removeFromItinerary(<%=entry.getKey()%>)">Remove from Itinerary</button>
                    </p>
            </div>
        </div>
    <%}%>
    </form>
    <%} else {%>
        <div id="currentItineraryTitle"><b>You have not added any places.</b></div>
    <%}%>
</div>

<div id="savedItineraries" >
    <div id="savedItineraryTitle"><b>Saved Itineraries</b></div>

</div>

</body>
</html>