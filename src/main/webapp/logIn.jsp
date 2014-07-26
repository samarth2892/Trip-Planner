<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% String error = String.valueOf(request.getAttribute("error")); %>
    <title>Trip Planner</title>
    <script src="JavaScript/jquery-1.11.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="stylesheets/style.css">
    <link rel="stylesheet" type="text/css" href="Fonts/stylesheet.css">

</head>
<body>
<div id="form" class="loginForm">
    <form action="AccountLogIn" method="POST">
        <%= ((error.equals("null"))?"":error) %>
        <input name="username" type="text" placeholder="Username"/>
        <input name="password" type="password" placeholder="Password" />
        <a href = "http://localhost:8080/TripPlanner/forgotPassword.jsp"> Forgot your password? </a>
        <input id="loginButton" type="submit" value="" />
        or <br/>
        <a id="HomePageText" href = "http://localhost:8080/TripPlanner/index.jsp" title = "Click here to Return to home page">
            Click here to return to home page</a>
    </form>
</div>

</body>
</html>
