<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <%  String error = String.valueOf(request.getAttribute("error"));
        int errorCount = 0;
        if(!error.equals("null")) {
            errorCount =
                    Integer.parseInt((String) request.getAttribute("errorCount"));
        }
       int formHeight = 290 + 20*errorCount;

    %>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="stylesheets/style.css">
    <link rel="stylesheet" type="text/css" href="Fonts/stylesheet.css">
</head>

<body>
    <div id="form" class="signUpForm" style="height:<%= formHeight%>px">
        <form action="Create" method="POST">
            <%= ((error.equals("null"))?"":error) %>
            <input name="operation" type="hidden" value="create" />
            <input name="name" type="text" placeholder="Your Name"/>
            <input name="username" type="text" placeholder="Create a Username" />
            <input name="createPassword" type="password" placeholder="Create a Password" />
            <input name="confirmPassword" type="password" placeholder="Confirm Password" />
            <input name= "email" type= "text" placeholder = "Email" />
            <input id="SignUpButton" type="submit" value="" />
            or <br/>
            <a id="HomePageText" href = "http://localhost:8080/TripPlanner/index.jsp" title = "Click here to Return to home page">
                Click here to return to home page</a>
        </form>
    </div>
</body>
</html>