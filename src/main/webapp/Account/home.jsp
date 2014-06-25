<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Place" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <Title>Home</Title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/home.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Fonts/stylesheet.css">
    <script src="<%=request.getContextPath()%>/JavaScript/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=true_or_false"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/JavaScript/jsFunctions.js"></script>
</head>
<body onload="autoComplete()">
<div id="NavBar">
    <div id="topLeftText"> Welcome  <%= request.getSession().getAttribute("userStatus")%></div>
    <div id="links">
        <span id="homeLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Home</a></span>
        <span id="accLink"><a href="<%=request.getContextPath()%>/Account/settings.jsp">Account Settings</a></span>
        <span id="logoutLink"><a href="<%=request.getContextPath()%>/LogOut"> LogOut</a></span>
    </div>
</div>

<div id = "SearchBar">
    <form action="<%=request.getContextPath()%>/Account/Search" method="POST">
        <ul>
            <li><input id="address" name="address" type="text" placeholder="Location" style="width: 23%"
                    onblur="geocode()"/></li>
            <li><input name="keyword" type="text" placeholder="Search" style="width: 12%" /></li>
            <li><input id="date" name="date" type="text" placeholder="dd/mm/yyyy" style="width: 10%"
                       onblur="dateValidation()" />
                <input type="hidden" id="day" name="day" value="">
            </li>
            <li>
                <select name="transportation">
                    <option value="" disabled selected>Transportation</option>
                    <option value="car">Car</option>
                    <option value="bike">Bicycle</option>
                    <option value="bus">Bus</option>
                    <option value="walk">Walk</option>
                </select>
            </li>

            <li>
                <select name="startHour">
                    <option value="" disabled selected>From</option>
                    <option value="0100"> 1</option>
                    <option value="0200"> 2</option>
                    <option value="0300"> 3</option>
                    <option value="0400"> 4</option>
                    <option value="0500"> 5</option>
                    <option value="0600"> 6</option>
                    <option value="0700"> 7</option>
                    <option value="0800"> 8</option>
                    <option value="0900"> 9</option>
                    <option value="1000"> 10</option>
                    <option value="1100"> 11</option>
                    <option value="0000"> 12</option>

                </select>
                <select name="startAMPM">
                    <option value = 'am'> AM </option>
                    <option value = 'pm'> PM </option>
                </select>
                <select name="endHour">
                    <option value="" disabled selected>To</option>
                    <option value="0100"> 1</option>
                    <option value="0200"> 2</option>
                    <option value="0300"> 3</option>
                    <option value="0400"> 4</option>
                    <option value="0500"> 5</option>
                    <option value="0600"> 6</option>
                    <option value="0700"> 7</option>
                    <option value="0800"> 8</option>
                    <option value="0900"> 9</option>
                    <option value="1000"> 10</option>
                    <option value="1100"> 11</option>
                    <option value="0000"> 12</option>
                </select>
                <select name="endAMPM">
                    <option value = 'am'> AM </option>
                    <option value = 'pm'> PM </option>
                </select>
            </li>

            <li>
                <select name="minPrice">
                    <option value="" disabled selected>Price Range</option>
                    <option value = '0'> $ </option>
                    <option value = '1'> $$ </option>
                    <option value = '2'> $$$ </option>
                    <option value = '3'> $$$$ </option>
                    <option value = '4'> $$$$$ </option>
                </select>
            </li>
            <li>
                <select name="minRating">
                    <option value="" disabled selected>Rating</option>
                    <option value = '1.0'> 1 </option>
                    <option value = '2.0'> 2 </option>
                    <option value = '3.0'> 3 </option>
                    <option value = '4.0'> 4 </option>
                    <option value = '5.0'> 5 </option>
                </select>
            </li>
            <li>
                <select name="maxDistance">
                    <option value="" disabled selected>Within</option>
                    <option value = '1'> 1 mile</option>
                    <option value = '2'> 2 miles</option>
                    <option value = '3'> 3 miles</option>
                    <option value = '4'> 4 miles</option>
                    <option value = '5'> 5 miles</option>
                    <option value = '10'> 10 miles</option>
                    <option value = '15'> 15 miles</option>
                    <option value = '20'> 20 miles</option>
                    <option value = '30'> 30 miles</option>
                </select>
            </li>
            <li>
                <input id="searchButton" type="submit" value="" />
            </li>
        </ul>
    </form>
</div>

<div id="map"></div>

<div id="searchResults">
    <% ArrayList<Place> places = (ArrayList<Place>) request.getAttribute("placeResult");
        if(places != null) {%>
            <h4 style="color: lightsteelblue;text-align: center;">Click on a place to see it on the map and more details</h4>

            <script type="text/javascript">
                var mapCenter = new google.maps.LatLng(<%=request.getAttribute("center")%>);
                map = new google.maps.Map(document.getElementById('map'), {
                    center: mapCenter,
                    zoom: 10
                });

                var infoWindow = new google.maps.InfoWindow();
            </script>
            <%for(int x = 0; x < places.size(); x++) {%>
            <a href="javascript:show(<%=x%>)" id="<%=x%>" style="color: dimgrey;text-align: center;">
                <h3><%=places.get(x).getName()%></h3></a>
                <p style="text-align: center;">Rating:<%=places.get(x).getRating()%></p>
                <script type="text/javascript">
                    var placeLocation
                            = new google.maps.LatLng(<%=places.get(x).getLatitude()%>,<%=places.get(x).getLongitude()%>);
                    var contentString = "<div id='content'>"
                            +"<h3><%=places.get(x).getName()%></h3>"
                            +"<p>Address <%=places.get(x).getAddress()%><br/>"
                            +"Phone No. <%=places.get(x).getPhoneNumber()%><br/>"
                            +"Rating: <%=places.get(x).getRating()%><br/>"
                            +"Open Time: <%=places.get(x).getOpenTime()%><br/>"
                            +"Close Time: <%=places.get(x).getCloseTime()%><br/>"
                            +"More info: <%=places.get(x).getWebsite()%></p>"
                            +"<div style='width:100%;height: 150px;overflow-y: auto'>"
                            +"<% if(null != places.get(x).getReviews()) {
                                for(String review: places.get(x).getReviews()) {%>"
                            +"<%=review%><br/>"
                            +"<%}
                                }%>"
                            +"</div>"
                            +"</div>";

                    createMarker(placeLocation,contentString,<%=x%>);
                </script>

            <%}

        } else if( null != request.getAttribute("resultsStatus") &&
                    request.getAttribute("resultsStatus").equals("null")){%>
            <h3>No Results found!</h3>
        <%}%>
</div>

</body>

</html>