<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="stylesheets/style.css">
</head>

<body>
    <div id="form" action="/Create" class="signUpForm">
        <form>
            <input name= "name" type="text" placeholder="Your Name"/>
            <input name="username" type="text" placeholder="Create a Username" />
            <input name="password" type="password" placeholder="Create a Password" />
            <input name="password" type="password" placeholder="Confirm Password" />
            <input id="SignUpButton" type="submit" value="" />
            or <br/>
            <a id="LogInText"href = "index.jsp" title = "Click here to sign up">
                Log In</a>
        </form>
    </div>
</body>
</html>