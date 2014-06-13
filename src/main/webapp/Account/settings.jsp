<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <Title>Home</Title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/home.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Fonts/stylesheet.css">
        <%  String changeUsernameError = String.valueOf(request.getAttribute("changeUsernameError"));
            String changePasswordError = String.valueOf(request.getAttribute("changePasswordError"));
            int errorCount = 0;
            if(!changeUsernameError.equals("null")) {
                errorCount =
                        Integer.parseInt((String) request.getAttribute("errorCount"));
            } else if(!changePasswordError.equals("null")) {
                errorCount =
                        Integer.parseInt((String) request.getAttribute("errorCount"));
            }

            int formHeight = 300 + 20*errorCount;
        %>
    </head>
    <body>
        <div id="NavBar">
            <div id="topLeftText">Account Settings</div>
            <div id="links">
                <span id="homeLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Home</a></span>
                <span id="accLink"><a href="<%=request.getContextPath()%>/Account/home.jsp">Account Settings</a></span>
                <span id="logoutLink"><a href="<%=request.getContextPath()%>/LogOut"> LogOut</a></span>
            </div>
        </div>
        <div id="formContainer">
            <div id="changeCredentialsForm" style="height:<%= formHeight%>px" >
                <div id="changeUsernameForm">
                    <form action="<%=request.getContextPath()%>/Account/updateUsername"  method="POST">
                            <h3>Change your Username</h3>
                            <%= ((changeUsernameError.equals("null"))?"":changeUsernameError) %>
                            <input name="newUsername" type="text" placeholder="New Username" />
                            <input name="password" type="password" placeholder="Password" />
                            <input id="changeUsernameButton" type="submit" value="" />
                    </form>
                </div>
                <div id="changePasswordForm">
                    <form action="<%=request.getContextPath()%>/Account/updatePassword"  method="POST">
                            <h3>Change your Password</h3>
                            <%= ((changePasswordError.equals("null"))?"":changePasswordError) %>
                            <input name="password" type="password" placeholder="Current Password" />
                            <input name="newPassword" type="password" placeholder="New Password" />
                            <input name="confirmPassword" type="password" placeholder="Confirm Password" />
                            <input id="changePasswordButton" type="submit" value="" />
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>