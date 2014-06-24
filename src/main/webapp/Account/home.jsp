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
                <select name="hours">
                    <option value="1"> 1</option>
                    <option value="2"> 2</option>
                    <option value="3"> 3</option>
                    <option value="4"> 4</option>
                    <option value="5"> 5</option>
                    <option value="6"> 6</option>
                    <option value="7"> 7</option>
                    <option value="8"> 8</option>
                    <option value="9"> 9</option>
                    <option value="10"> 10</option>
                    <option value="11">11 </option>
                    <option value="12"> 12</option>
                </select>
                <select>
                    <option value = 'am'> AM </option>
                    <option value = 'pm'> PM </option>
                </select>
                to
                <select>
                    <option value="1"> 1</option>
                    <option value="2"> 2</option>
                    <option value="3"> 3</option>
                    <option value="4"> 4</option>
                    <option value="5"> 5</option>
                    <option value="6"> 6</option>
                    <option value="7"> 7</option>
                    <option value="8"> 8</option>
                    <option value="9"> 9</option>
                    <option value="10"> 10</option>
                    <option value="11">11 </option>
                    <option value="12"> 12</option>
                </select>
                <select>
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
            <li><input id="searchButton" type="Submit" value="Submit" /></li>
        </ul>
    </form>
</div>

<div id="googleMaps">
    <% ArrayList<Place> places = (ArrayList<Place>) request.getAttribute("placeResult");
        if(places != null) {
            for(Place place: places) {%>
                <div id="placeResultDiv" > <%=place.getName()%></div>
                <div id="placeResultDiv" > <%=place.getAddress()%></div>
                <div id="placeResultDiv" > <%=place.getRating()%></div>
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