<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Directions</title>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/home.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Fonts/stylesheet.css">
  <link rel="stylesheet" type = "text/css" href="<%=request.getContextPath()%>/stylesheets/print.css"/>
  <script type="text/javascript" src="<%=request.getContextPath()%>/JavaScript/facebook.js"></script>
</head>

<body>
<div class="fb-share-button" data-href="<%=request.getContextPath()%>/Account/directions.jsp" data-width="50px"></div>
    <div id="content" >

        <ul>
            <%
            ArrayList<String> directions = (ArrayList<String>) request.getSession().getAttribute("directions");
            for (String route : directions) {%>
                <li><%=route%></li><br/>
            <%}%>
        </ul>
    </div>
</body>
</html>