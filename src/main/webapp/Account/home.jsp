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
    <script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
</head>
<body onload="initialize()">
<div id="loadingDiv"></div>
<script type="text/javascript">$('#loadingDiv').fadeIn('fast');</script>
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
            <li><input id="date" name="date" type="text" style="width: 10%" placeholder="mm-dd-yyyy"
                       onblur="dateValidation()"  title="Default is current date." />
                <input type="hidden" id="day" name="day" value="0">
            <li><input name="keyword" type="text" placeholder="Search" style="width: 12%" /></li>
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
                <input id="searchButton" type="submit" value="" onClick="$('#loadingDiv').fadeIn('fast');"/>
            </li>
        </ul>
    </form>
</div>

<div id="map"></div>
<div id="moreInfo" style="text-align:center;" ><a style="padding-top:10px" href='javascript:hideMoreInfo()'>Click here to exit</a></div>
<div id="searchResults" style="text-align: center;">
    <% ArrayList<Place> places = (ArrayList<Place>) request.getAttribute("placeResult");
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
                           +"<div style='width:40%;float:right;'>"
                           +"Address: <%=(null!=places.get(x).getAddress())?places.get(x).getAddress():""%><br/>"
                           +"Phone No: <%=(null!=places.get(x).getPhoneNumber())?places.get(x).getPhoneNumber():""%><br/>"
                           +"Rating: <%=(null!=places.get(x).getRating())?places.get(x).getRating():""%><br/>"
                           +"Hours on: <%=date%><br/>"
                           +"Open Time: <%=(null!=places.get(x).getOpenTimeString())?places.get(x).getOpenTimeString():"N/A"%><br/>"
                           +"Close Time: <%=(null!=places.get(x).getCloseTimeString())?places.get(x).getCloseTimeString():"N/A"%><br/>"
                           +"<a href='<%=places.get(x).getWebsite()%>' target='_blank'>Click here to open website.</a><br/>"
                           +"<a href='javascript:showMoreInfo(<%=x%>)'>Click here to see  more reviews and photos.</a>"
                           +"<br/><button type='button'>Add to Itinerary</button>"
                           +"</div>"
                           +"</div>";

                    createMarker(placeLocation,contentString,<%=x%>);
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
                <%}%>

        <% } else if( null != request.getAttribute("resultsStatus") &&
                    request.getAttribute("resultsStatus").equals("null")){%>
            <h3>No Results found!</h3>
         <%}%>
</div>

</body>
<script type="text/javascript">$('#loadingDiv').fadeOut('fast');</script>
</html>