<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Place" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.PlaceDb" %>
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
    <script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
</head>
<body onload="initialize()">
<div id="loadingDiv"></div>

<div id="NavBar">
    <div id="topLeftText"> Welcome  <%= request.getSession().getAttribute("userStatus")%></div>
    <div id="links">
        <span id="homeLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Home</a></span>
        <span id="itinLink"><a href="<%=request.getContextPath()%>/Account/itineraries.jsp">Itineraries</a></span>
        <span id="accLink"><a href="<%=request.getContextPath()%>/Account/settings.jsp">Account Settings</a></span>
        <span id="logoutLink"><a href="<%=request.getContextPath()%>/LogOut">LogOut</a></span>
    </div>
</div>

<div id = "SearchBar">
    <form action="<%=request.getContextPath()%>/Account/Search" method="POST" onSubmit="return validate();">
        <ul>
            <li><input id="address" name="address" type="text" placeholder="Location" style="width: 23%"
                        onkeyup="geocode();" value="${param.address}" /></li>
            <li><input id="date" name="date" type="text" style="width: 10%" placeholder="mm-dd-yyyy"
                       value="${param.date}"/>
                <input type="hidden" id="day" name="day" value="0">
            <li><input name="keyword" type="text" placeholder="Search" style="width: 12%" value="${param.keyword}" /></li>
            <li>
                <select name="transportation">
                    <option value="" disabled selected>Transportation</option>
                    <option value="car" ${param.transportation == 'car' ? 'selected' : ''}>Car</option>
                    <option value="bike" ${param.transportation == 'bike' ? 'selected' : ''}>Bicycle</option>
                    <option value="bus" ${param.transportation == 'bus' ? 'selected' : ''}>Bus</option>
                    <option value="walk" ${param.transportation == 'walk' ? 'selected' : ''}>Walk</option>
                </select>
            </li>

            <li>
                <select name="startHour">
                    <option value="" disabled selected>From</option>
                    <option value="0100" ${param.startHour == '0100' ? 'selected' : ''}> 1</option>
                    <option value="0200" ${param.startHour == '0200' ? 'selected' : ''}> 2</option>
                    <option value="0300" ${param.startHour == '0300' ? 'selected' : ''}> 3</option>
                    <option value="0400" ${param.startHour == '0400' ? 'selected' : ''}> 4</option>
                    <option value="0500" ${param.startHour == '0500' ? 'selected' : ''}> 5</option>
                    <option value="0600" ${param.startHour == '0600' ? 'selected' : ''}> 6</option>
                    <option value="0700" ${param.startHour == '0700' ? 'selected' : ''}> 7</option>
                    <option value="0800" ${param.startHour == '0800' ? 'selected' : ''}> 8</option>
                    <option value="0900" ${param.startHour == '0900' ? 'selected' : ''}> 9</option>
                    <option value="1000" ${param.startHour == '1000' ? 'selected' : ''}> 10</option>
                    <option value="1100" ${param.startHour == '1100' ? 'selected' : ''}> 11</option>
                    <option value="0000" ${param.startHour == '0000' ? 'selected' : ''}> 12</option>

                </select>
                <select name="startAMPM">
                    <option value = 'am' ${param.startAMPM == 'am' ? 'selected' : ''}> AM </option>
                    <option value = 'pm' ${param.startAMPM == 'pm' ? 'selected' : ''}> PM </option>
                </select>
                <select name="endHour">
                    <option value="" disabled selected>To</option>
                    <option value="0100" ${param.endHour == '0100' ? 'selected' : ''}> 1</option>
                    <option value="0200" ${param.endHour == '0200' ? 'selected' : ''}> 2</option>
                    <option value="0300" ${param.endHour == '0300' ? 'selected' : ''}> 3</option>
                    <option value="0400" ${param.endHour == '0400' ? 'selected' : ''}> 4</option>
                    <option value="0500" ${param.endHour == '0500' ? 'selected' : ''}> 5</option>
                    <option value="0600" ${param.endHour == '0600' ? 'selected' : ''}> 6</option>
                    <option value="0700" ${param.endHour == '0700' ? 'selected' : ''}> 7</option>
                    <option value="0800" ${param.endHour == '0800' ? 'selected' : ''}> 8</option>
                    <option value="0900" ${param.endHour == '0900' ? 'selected' : ''}> 9</option>
                    <option value="1000" ${param.endHour == '1000' ? 'selected' : ''}> 10</option>
                    <option value="1100" ${param.endHour == '1100' ? 'selected' : ''}> 11</option>
                    <option value="0000" ${param.endHour == '0000' ? 'selected' : ''}> 12</option>
                </select>
                <select name="endAMPM">
                    <option value = 'am' ${param.endAMPM == 'am' ? 'selected' : ''}> AM </option>
                    <option value = 'pm' ${param.endAMPM == 'pm' ? 'selected' : ''}> PM </option>
                </select>
            </li>

            <li>
                <select name="minPrice">
                    <option value="" disabled selected>Price Range</option>
                    <option value = '0' ${param.minPrice == '0' ? 'selected' : ''}> $ </option>
                    <option value = '1' ${param.minPrice == '1' ? 'selected' : ''}> $$ </option>
                    <option value = '2' ${param.minPrice == '2' ? 'selected' : ''}> $$$ </option>
                    <option value = '3' ${param.minPrice == '3' ? 'selected' : ''}> $$$$ </option>
                    <option value = '4' ${param.minPrice == '4' ? 'selected' : ''}> $$$$$ </option>
                </select>
            </li>
            <li>
                <select name="minRating">
                    <option value="" disabled selected>Rating</option>
                    <option value = '1.0' ${param.minRating == '1.0' ? 'selected' : ''}> 1 </option>
                    <option value = '2.0' ${param.minRating == '2.0' ? 'selected' : ''}> 2 </option>
                    <option value = '3.0' ${param.minRating == '3.0' ? 'selected' : ''}> 3 </option>
                    <option value = '4.0' ${param.minRating == '4.0' ? 'selected' : ''}> 4 </option>
                    <option value = '5.0' ${param.minRating == '5.0' ? 'selected' : ''}> 5 </option>
                </select>
            </li>
            <li>
                <select name="maxDistance">
                    <option value="" disabled selected>Within</option>
                    <option value = '1' ${param.maxDistance == '1' ? 'selected' : ''}> 1 mile</option>
                    <option value = '2' ${param.maxDistance == '2' ? 'selected' : ''}> 2 miles</option>
                    <option value = '3' ${param.maxDistance == '3' ? 'selected' : ''}> 3 miles</option>
                    <option value = '4' ${param.maxDistance == '4' ? 'selected' : ''}> 4 miles</option>
                    <option value = '5' ${param.maxDistance == '5' ? 'selected' : ''}> 5 miles</option>
                    <option value = '10' ${param.maxDistance == '10' ? 'selected' : ''}> 10 miles</option>
                    <option value = '15' ${param.maxDistance == '15' ? 'selected' : ''}> 15 miles</option>
                    <option value = '20' ${param.maxDistance == '20' ? 'selected' : ''}> 20 miles</option>
                    <option value = '30' ${param.maxDistance == '30' ? 'selected' : ''}> 30 miles</option>
                </select>
                <input id="searchButton" type="submit" value="" onClick=""/>
            </li>
        </ul>
    </form>
</div>

<div id="map"></div>
<div id="moreInfo" style="text-align:center;" ><a href='javascript:hideMoreInfo()'>Click here to exit</a></div>
<div id="searchResults" style="text-align: center;">
    <% ArrayList<Place> places = (ArrayList<Place>) request.getSession().getAttribute("placeResult");
        if(places != null) {%>
            <h4 style="color: lightsteelblue;"><%=places.size()%> Results found<br/>
                Click on a place to see it on the map and more details</h4>

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
                <p>Rating:<%=places.get(x).getRating()%></p>
                <%String date = request.getParameter("date");%>
                <script type="text/javascript">
                    var placeLocation
                            = new google.maps.LatLng(<%=places.get(x).getLatitude()%>,<%=places.get(x).getLongitude()%>);
                    var contentString = "<div id='content' style='max-width:600px;height:200px'>"
                           +"<h3 style='text-align:center'><%=(null!=places.get(x).getName())?places.get(x).getName():""%></h3>"
                           +"<div style='width:60%;float:left'>"
                           +"<p><img style='max-width:90%;max-height:90%;display: block;margin-left: auto;margin-right:auto' src='<%=places.get(x).getImageURL().get(0)%>'/><br/>"
                           +"</div>"
                           +"<div id='data' style='width:40%;float:right;'>"
                           +"Address: <%=(null!=places.get(x).getAddress())?places.get(x).getAddress():""%><br/>"
                           +"Phone No: <%=(null!=places.get(x).getPhoneNumber())?places.get(x).getPhoneNumber():""%><br/>"
                           +"Rating: <%=(null!=places.get(x).getRating())?places.get(x).getRating():""%><br/>"
                           +"Hours on: <%=date%><br/>"
                           +"Open Time: <%=(null!=places.get(x).getOpenTimeString())?places.get(x).getOpenTimeString():"N/A"%><br/>"
                           +"Close Time: <%=(null!=places.get(x).getCloseTimeString())?places.get(x).getCloseTimeString():"N/A"%><br/>"
                           +"<a href='<%=places.get(x).getWebsite()%>' target='_blank'>Click here to open website.</a><br/>"
                           +"<a href='javascript:showMoreInfo(<%=x%>)'>Click here to see  more reviews and photos.</a><br/>"
                           +"<button type='button' onclick='ajaxFunction(<%=x%>)'>Add to Itinerary</button>"
                           +"</div>"
                           +"</div>";

                    createMarker(placeLocation,contentString,<%=x%>);
                </script>

                <script language="javascript" type="text/javascript">
                    function ajaxFunction(x){
                        var ajaxRequest;

                        try{
                            // Opera 8.0+, Firefox, Safari
                            ajaxRequest = new XMLHttpRequest();
                        } catch (e){
                            // Internet Explorer Browsers
                            try{
                                ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
                            } catch (e) {
                                try{
                                    ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
                                } catch (e){
                                    // Something went wrong
                                    alert("Your browser broke!");
                                    return false;
                                }
                            }
                        }
                        // Create a function that will receive data sent from the server
                        ajaxRequest.onreadystatechange = function(){
                            if(ajaxRequest.readyState == 4){

                            }
                        }

                        ajaxRequest.open("POST", "/TripPlanner/Account/AddPlace?index=" + x, true);
                        ajaxRequest.send(null);
                    }
                </script>

                <!-- More info Divs-->
                <script type="text/javascript">
                   var moreInfoContent = "<div id='moreInfoContent<%=x%>' class='moreInfoContent'></div>";
                   var reviewsDiv = "<div id='reviewsDiv'><h3>Reviews</h3>";
                   var photosDiv = "<div id='photosDiv'><h3>Photos</h3>";
                    $("#moreInfo").append(moreInfoContent);

                    <% if(places.get(x).getReviews().size() > 0) {
                        for(int j = 0; j < places.get(x).getReviews().size(); j++) {
                             String review = places.get(x).getReviews().get(j).replace("\"", "\\\"");
                             review = review.replace("\'", "\\\'");
                             review = review.replace("\n", "+");
                        %>
                            reviewsDiv = reviewsDiv + "<br/><p style='text-align:left'><%=review%></p>";
                        <%}%>

                    <%} else {%>
                   reviewsDiv = reviewsDiv + "<br/><p style='text-align:left'>No Reviews Available</p>";
                    <%}%>

                   <% if(places.get(x).getImageURL().size() > 0) {
                       for(int j = 0; j < places.get(x).getImageURL().size(); j++) {
                            String url = places.get(x).getImageURL().get(j);
                       %>
                        photosDiv = photosDiv + "<br/><img style='max-width:90%;max-height:300px;display: block;margin-left: auto;margin-right:auto' src='<%=url%>'/>";
                      <%}%>

                   <%} else {%>
                        $("#photosDiv").css("width","0");
                   <%}%>

                    reviewsDiv = reviewsDiv + "</div>";
                    photosDiv = photosDiv + "</div>";
                    $("#moreInfoContent<%=x%>").append(reviewsDiv).append(photosDiv);
                </script>

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
                <%}%>

        <% } else if( null != request.getAttribute("resultsStatus") &&
                    request.getAttribute("resultsStatus").equals("null")){%>
            <h3>No Results found!</h3>
         <%}%>
</div>

</body>

</html>