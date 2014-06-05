<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Trip Planner</title>
    <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
    <div id="form">
        <form action="/TripPlanner/login" method="POST">
            UserName:<input name="username" type="text" /><br/>
            Password: <input name="password" type="password" />
            <input type="submit" value="Submit" />
        </form>
    </div>
  </body>
</html>
