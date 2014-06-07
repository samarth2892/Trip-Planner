<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Trip Planner</title>
    <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
    <a href = "/TripPlanner">
          <img src="homeIcon.png" alt="Back to Home Page" title="Back to Home Page" width="45px" height="40px"/>
     </a>

    <div id="form" >
        <form action="/TripPlanner/login" method="POST">
            <% String error = String.valueOf(request.getAttribute("error")); %>
            <%= ((error == "null")?"":error) %>
            UserName:<input name="username" type="text" /><br/>
            Password: <input name="password" type="password" />
            <input type="submit" value="Submit" />
        </form>
        <a href = "/TripPlanner/signUp.jsp" title = "Click here to sign up">
                       Sign Up</a>
    </div>

  </body>
</html>
