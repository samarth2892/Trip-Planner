<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <Title>Home</Title>
        <link rel="stylesheet" type="text/css" href="stylesheets/home.css">
        <% String name = String.valueOf(request.getAttribute("name")); %>
    </head>
    <body>
        <div id="NavBar">
            <Span>Welcome <%= ((name.equals("null"))?"":name) %></Span>
            <a href="/TripPlanner/Home"> <img src="images/homeIcon.png" /> </a>
        </div>
        <div id="googleMaps">
            <!--<iframe
                    width="100%"
                    height="100%"
                    frameborder="0" style="border:0"
                    src="https://www.google.com/maps/embed/v1/place?key=AIzaSyAekNru_w4ZwcjbMfMXwVK-TnFLtj4TQUM
                       &q=Space+Needle,Seattle+WA">
            </iframe>-->
        </div>
    </body>

</html>