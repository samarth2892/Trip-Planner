<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <% String error = String.valueOf(request.getAttribute("error")); %>
      <title>Trip Planner</title>
      <script src="JavaScript/jquery-1.11.0.min.js"></script>
      <link rel="stylesheet" type="text/css" href="stylesheets/style.css">
      <link rel="stylesheet" type="text/css" href="Fonts/stylesheet.css">
      <%  String forgotPasswordError = String.valueOf(request.getAttribute("forgotPasswordError"));
                  int errorCount = 0;
                  if(!forgotPasswordError.equals("null")) {
                      errorCount =
                              Integer.parseInt((String) request.getAttribute("errorCount"));
                  } else if(!forgotPasswordError.equals("null")) {
                      errorCount =
                              Integer.parseInt((String) request.getAttribute("errorCount"));
                  }

                  int formHeight = 300 + 20*errorCount;
      %>

  </head>
  <body>
    <div id="form" class = "forgotPasswordForm">
        <form action="forgotPassword" method="POST">
            <%= ((forgotPasswordError.equals("null"))?"":forgotPasswordError) %>
            <input name="username" type="text" placeholder="Username"/>
            <input name="email" type = "text" placeholder = "Email"/>
            <input id="emailAddress" type="submit" value="" />
            or <br/>
            <a id="SignUpText"href = "signUp.jsp" title = "Click here to sign up">
                           Create an account</a>
        </form>
    </div>

  </body>
</html>