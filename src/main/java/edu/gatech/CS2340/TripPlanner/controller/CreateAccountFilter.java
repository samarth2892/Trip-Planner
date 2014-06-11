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
        urlPatterns = {"/Create/*"}

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

        String error = null;
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String createPassword = request.getParameter("createPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (name.equals("")) error = "Please enter a name";
        else if (!validateUsername(username))
            error = "Username must contain one letter " +
                    "and be between 8 and 20 characters";
        else if (!validatePassword(createPassword))
            error = "Password must contain at least one number, one" +
                    " uppercase letter, one lowercase letter, and be" +
                    " between 8 and 20 characters";
        else if (!createPassword.equals(confirmPassword))
            error = "Passwords do not match";
        else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        request.setAttribute("error", error);
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean validateUsername(String username) {
        Pattern pattern;
        Matcher matcher;

        String usernamePattern = "(((?=.*[a-z])|(?=.*[A-Z])).{8,20})";

        pattern = Pattern.compile(usernamePattern);
        matcher = pattern.matcher(username);

        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        Pattern pattern;
        Matcher matcher;

        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";

        pattern = Pattern.compile(passwordPattern);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
