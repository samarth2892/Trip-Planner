<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Place" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Itinerary" %>
<%@ page import="java.util.ArrayList" %>
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
    <% String error = String.valueOf(request.getAttribute("error")); %>
</head>

<body>
<div id="loadingDiv"></div>

<div id="NavBar">
    <div id="topLeftText"> Welcome  <%= request.getSession().getAttribute("userStatus")%></div>
    <div id="links">
        <span id="homeLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Home</a></span>
        <span id="accLink"><a href="<%=request.getContextPath()%>/Account/settings.jsp">Account Settings</a></span>
        <span id="itineraryLink"><a href="<%=request.getContextPath()%>/Account/itinerary.jsp">Itinerary<span id="noOfPlaces"></span></a></span>
        <span id="logoutLink"><a href="<%=request.getContextPath()%>/LogOut">LogOut</a></span>
    </div>
</div>

<% Itinerary sessionItinerary
        = (Itinerary) request.getSession().getAttribute("sessionItinerary");
   HashMap<String, Place> itineraryPlaces
        = sessionItinerary.getMap();%>
<div id="currentItinerary">
    <%if(itineraryPlaces.size() > 0) {%>
    <form action="<%=request.getContextPath()%>/Account/finalizeItinerary" method="post">
    <div id="currentItineraryTitle"><b>Current Itinerary</b>
        <select style="margin-left:40px" name="transportation">
            <option value="" disabled selected>Transportation</option>
            <option value="driving" ${param.transportation == 'driving' ? 'selected' : ''}>Car</option>
            <option value="bicycling" ${param.transportation == 'bicycling' ? 'selected' : ''}>Bicycle</option>
            <option value="bus" ${param.transportation == 'bus' ? 'selected' : ''}>Bus</option>
            <option value="walking" ${param.transportation == 'walking' ? 'selected' : ''}>Walk</option>
        </select>
        <input style="margin-left:40px" type="submit" value="Save" />
        <input style="margin-left:40px" type="submit" name="directionsButton" value="Get Directions"/>
        <button type="button" onclick="javascript:startOver()">Start Over</button>
        <br/><span style="color: red"><b><%= ((error.equals("null"))?"":error) %></b></span>
    </div>
    <% for(Map.Entry<String, Place> entry : itineraryPlaces.entrySet()) {%>
        <div class="itineraryPlace" id="itineraryPlace-<%=entry.getKey()%>">
            <span class="helper"></span><img src="<%=entry.getValue().getImageURL().get(0)%>" />
            <div id="itineraryPlaceDetails">
                <h4><%=entry.getValue().getName()%> <span style="float: right">Order of visit:
                    <input name="<%=entry.getKey()%>-order" min="1" max="<%=itineraryPlaces.size()%>"
                           type="number" style="width:40px" value="<%=request.getParameter(entry.getKey() + "-order")%>"/></span></h4>

                    <p> Address: <%=entry.getValue().getAddress()%><br/>
                        Hours on: <%=request.getSession().getAttribute("sessionDate").toString()%>
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
    <% ArrayList<Itinerary> savedSessionItineraries
            = (ArrayList<Itinerary>) request.getSession().getAttribute("savedSessionItineraries");%>
    <div id="savedItineraryTitle"><b>Saved Itineraries</b></div>
    <%if(savedSessionItineraries.size() > 0) {
        int count = 1;%>
        <%for(Itinerary itinerary : savedSessionItineraries) {%>
            <div id="savedItinerary">
                <form action="<%=request.getContextPath()%>/Account/showSavedItinerary" method="get">
                    <h3>Itinerary <%=count%> </h3>
                        <span id="savedItineraryContent" >
                            <b>Date: <%=itinerary.getDate()%></b><br/>
                            <b>Places near: <br/>
                               <%=itinerary.getOrigin()%>
                            </b><br/>
                            <input type="hidden" name="savedItineraryId" value="<%=(count - 1)%>"/>
                        </span>
                    <input type="submit" value="Show this itinerary" />
                </form>
            </div>
        <% count++;
          }%>
    <%}%>
</div>

<%if(request.getAttribute("directionsStatus") != null
        && request.getAttribute("directionsStatus").equals("OK")){%>
<script type="text/javascript">
    var popup =
    window.open(encodeURI('<%=request.getContextPath()%>'
                    +'/Account/directions.jsp'));
    if(!popup) {
        alert("Please turn off popup blocker");
    }
</script>
<%}%>
</body>
</html>