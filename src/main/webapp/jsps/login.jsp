<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="stylesheets/style.css">
  </head>
  <body>
    <a href = "/TripPlanner">
        <img src="../images/homeIcon.png" alt="Back to Home Page" title="Back to Home Page" width="45px" height="40px"/>
    </a>

    <div id="form" >
        <form action="/TripPlanner/login" method="POST">
            <%= request.getAttribute("error") %>
            Username:<input name="username" type="text" /><br/>
            Password: <input name="password" type="password" />

                <input type="submit" value="Submit" />

        </form>
    </div>
  </body>
</html>