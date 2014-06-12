package main.java.edu.gatech.CS2340.TripPlanner.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

@WebFilter (
        filterName = "UpdateUsernameFilter",
        urlPatterns = { "/updateUsername" }
)


public class UpdateUsernameFilter implements Filter {

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

        String newUsername = request.getParameter("newUsername");
        String password = request.getParameter("password");

        if (!validateUsername(newUsername)) {
            error.append("Username must contain one letter" +
                    "<br/>and be between 8 and 20 characters");
            errorCount = errorCount + 2;
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
}
