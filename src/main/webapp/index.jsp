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
<div id="form" class="homepageForm">
    <form action="LogIn" method="POST">
        <input id="loginButton" type="submit" value="" />
    </form>
    <form action = "signUp" method ="POST" title = "Click here to sign up">
        <input id="signUpButton" type="submit" value=""/>
    </form>
</div>

</body>
</html>
