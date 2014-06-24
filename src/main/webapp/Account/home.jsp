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
            <li><input id="address" name="address" type="text" placeholder="Location" style="width: 23%"/></li>
            <li><input name="keyword" type="text" placeholder="Search" style="width: 12%" /></li>
            <li><input name="day" type="text" placeholder="dd" style="width: 3%"/></li>
            <li><input name="month" type="text" placeholder="mm" style="width: 3%"/></li>
            <li><input name="year" type="text" placeholder="yyyy" style="width: 4%"/></li>
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
                    <option value="1100">11 </option>
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
                    <option value="1100">11 </option>
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
                <input id="searchButton" type="submit" value="" />
            </li>
        </ul>
    </form>
</div>

<div id="searchResults">
    <% ArrayList<Place> places = (ArrayList<Place>) request.getAttribute("placeResult");
        if(places != null) {
            for(Place place: places) {%>
                <div id="placeResultDiv" >Name: <%=place.getName()%></div>
                <div id="placeResultDiv" >Address: <%=place.getAddress()%></div>
                <div id="placeResultDiv" >Rating: <%=place.getRating()%></div>
                <div id="placeResultDiv" >Open Time: <%=place.getOpenTime()%></div>
                <div id="placeResultDiv" >Close Time: <%=place.getCloseTime()%></div>
                <div id="placeResultDiv" ></div>
                <!--<iframe
                        width="100%"
                        height="100%"
                        frameborder="0" style="border:0"
                        src="https://www.google.com/maps/embed/v1/place?key=AIzaSyAekNru_w4ZwcjbMfMXwVK-TnFLtj4TQUM
                           &q=Space+Needle,Seattle+WA">
                </iframe>-->
            <%}
        }%>
</div>
</body>

</html>