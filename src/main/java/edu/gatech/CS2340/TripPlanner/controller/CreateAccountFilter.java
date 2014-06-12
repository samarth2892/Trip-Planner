package main.java.edu.gatech.CS2340.TripPlanner.controller;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

@WebFilter(
        filterName= "CreateAccountFilter",
        urlPatterns = {
                "/Create/*",
                "/updateUsername",
                "/updatePassword"
        }
)

public class CreateAccountFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("signUp.jsp");

        StringBuffer error = new StringBuffer("");

        int errorCount = 0;

        String operation = request.getParameter("operation");

        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String createPassword = request.getParameter("createPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if(operation.equals("create")) {
            name = request.getParameter("name");
            username = request.getParameter("username");
            createPassword = request.getParameter("createPassword");
            confirmPassword = request.getParameter("confirmPassword");

        } else if (operation.equals("updateUsername")) {

        } else if (operation.equals("updatePassword")) {

        }
        if (name.equals("")) {
            error.append("Please enter a name");
            errorCount++;
        } else if (!validateUsername(username)) {
            error.append("Username must contain one letter" +
                    "<br/>and be between 8 and 20 characters");
            errorCount = errorCount + 2;
        }

        if (!validatePassword(createPassword)) {
            error.append("<br/>Password must contain at least" +
                    "<br/>one number, one uppercase " +
                    "<br/>letter, one lowercase letter," +
                    "<br/>and should be between 8 and 20" +
                    "<br/>characters");
            errorCount = errorCount + 6;
        } else if (!createPassword.equals(confirmPassword)) {
            error.append("<br/>Passwords do not match");
            errorCount++;
        }

        if (!error.toString().equals("")) {
            request.setAttribute("error", error);
            request.setAttribute("errorCount", Integer.toString(errorCount));
            dispatcher.forward(request, response);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean validateUsername(String username) {
        Pattern pattern;
        Matcher matcher;

        String usernamePattern =
                "(((?=.*[a-z])|(?=.*[A-Z])).{8,20})";

        pattern = Pattern.compile(usernamePattern);
        matcher = pattern.matcher(username);

        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        Pattern pattern;
        Matcher matcher;

        String passwordPattern =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";

        pattern = Pattern.compile(passwordPattern);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
