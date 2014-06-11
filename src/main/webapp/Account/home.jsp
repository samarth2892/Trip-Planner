<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <Title>Home</Title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/home.css">
    </head>
    <body>
        <div id="NavBar">
            <div id="welcome"> Welcome  <%= request.getSession().getAttribute("userStatus")%></div>
            <div id="links">
                <span id="homeLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Home</a></span>
                <span id="accLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Account Settings</a></span>
                <span id="logoutLink"><a href="<%=request.getContextPath()%>/LogOut"> LogOut</a></span>
            </div>
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