<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Place" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.PlaceDb" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <Title>Itineraries</Title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/home.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Fonts/stylesheet.css">
    <script src="<%=request.getContextPath()%>/JavaScript/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/JavaScript/jsFunctions.js"></script>
    <script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>

</head>
<body>
<div id="NavBar">
    <div id="topLeftText">Itineraries</div>
    <div id="links">
        <span id="homeLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Home</a></span>
        <span id="itinLink"><a href="<%=request.getContextPath()%>/Account/itineraries.jsp">Itineraries</a></span>
        <span id="accLink"><a href="<%=request.getContextPath()%>/Account/settings.jsp">Account Settings</a></span>
        <span id="logoutLink"><a href="<%=request.getContextPath()%>/LogOut"> LogOut</a></span>
    </div>
</div>
<div id="formContainer">
    <%
        PlaceDb placeDb = new PlaceDb();
        ArrayList<Place> places = placeDb.loadItinerary();

        if(places != null) {%>
    <%for(int x = 0; x < places.size(); x++) {%>

    <a id="<%=x%>" style="color: dimgrey;text-align: center; top: 100px;">
        <h3><%=places.get(x).getName()%></h3>
        <h3><%=places.get(x).getAddress()%></h3>
        <h3><%=places.get(x).getPhoneNumber()%></h3></a>
        
            <a href=javascript:showDirections(<%=x%>)>Click here to get directions.</a>

        <script type="text/javascript">
            var directionsContent = "<div id='directionsContent<%=x%>' class='directionsContent'></div>";
            var directionsDiv = "<div id='directionsDiv'><h3>Directions</h3>";
            $("#directions").append(directionsContent);

            <% if(places.get(x).getDirections().size() > 0) {
                for(int j = 0; j < places.get(x).getDirections().size(); j++) {
                     String step = places.get(x).getDirections().get(j).replace("\"", "\\\"");
                     step = step.replace("\'", "\\\'");
                     step = step.replace("\n", "+");
                %>
            directionsDiv = directionsDiv + "<br/><p style='text-align:left'><%=step%></p>";
            <%}%>

            <%} else {%>
            directionsDiv = directionsDiv + "<br/><p style='text-align:left'>Directions not Available</p>";
            <%}%>

            directionsDiv = directionsDiv + "</div>";
            $("#directionsContent<%=x%>").append(directionsDiv);
        </script>

    <%}
    }%>
</div>
</body>
</html>