<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.edu.gatech.CS2340.TripPlanner.model.Place" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <Title>Home</Title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/home.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Fonts/stylesheet.css">
</head>
<body>
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
            <li><input name="address" type="text" placeholder="Location" size="30"/></li>
            <li><input name="keyword" type="text" placeholder="Search" size="15" /></li>
            <li><input name="day" type="text" placeholder="Day" size="1"/></li>
            <li><input name="month" type="text" placeholder="Month" size="4"/></li>
            <li><input name="year" type="text" placeholder="Year" size="2"/></li>
            <li>Transportation:
                <select name="transportation">
                    <option value="car">Car</option>
                    <option value="bike">Bicycle</option>
                    <option value="bus">Bus</option>
                    <option value="walk">Walk</option>
                </select>
            </li>
            <li>Hours:
                <select name="startHour">
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
                to
                <select name="endHour">
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

            <li>Price Range
                <select name="minPrice">
                    <option value = '0'> $ </option>
                    <option value = '1'> $$ </option>
                    <option value = '2'> $$$ </option>
                    <option value = '3'> $$$$ </option>
                    <option value = '4'> $$$$$ </option>
                </select>
            </li>
            <li>Rating
                <select name="minRating">
                    <option value = '1.0'> 1 </option>
                    <option value = '2.0'> 2 </option>
                    <option value = '3.0'> 3 </option>
                    <option value = '4.0'> 4 </option>
                    <option value = '5.0'> 5 </option>
                </select>
            </li>
            <li>Within
                <select name="maxDistance">
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
            <li><input id="searchButton" type="Submit" value="Submit" /></li>
        </ul>
    </form>
</div>

<div id="googleMaps">
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