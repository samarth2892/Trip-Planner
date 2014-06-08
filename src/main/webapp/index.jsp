<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <% String error = String.valueOf(request.getAttribute("error")); %>
      <title>Trip Planner</title>
      <script src="JavaScript/jquery-1.11.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="stylesheets/style.css">

  </head>
  <body>
    <div id="form" class="loginForm" >
        <form action="/TripPlanner/login" method="POST">
            <%= ((error.equals("null"))?"":error) %>
            <input name="username" type="text" placeholder="Username"/>
            <input name="password" type="password" placeholder="Password" />
            <input id="loginButton" type="submit" value="" />
            or <br/>
            <a id="SignUpText"href = "jsps/signUp.jsp" title = "Click here to sign up">
                           Create an account</a>
        </form>
    </div>

  </body>
</html>
