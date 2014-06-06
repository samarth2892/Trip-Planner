<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Trip Planner</title>
    <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
    <div id="form" >
        <form action="/TripPlanner/login" method="POST">
            <%= request.getAttribute("error") %>
            Username:<input name="username" type="text" /><br/>
            Password: <input name="password" type="password" />
            <center>
                <input type="submit" value="Submit" />
            </center>
        </form>
    </div>
  </body>
</html>