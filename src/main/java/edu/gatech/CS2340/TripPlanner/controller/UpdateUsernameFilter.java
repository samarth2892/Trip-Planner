package main.java.edu.gatech.CS2340.TripPlanner.controller;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(
        filterName = "UpdateUsernameFilter",
        urlPatterns = {"/Account/updateUsername" }
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
                request.getRequestDispatcher("/Account/settings.jsp");

        StringBuffer error = new StringBuffer("");

        int errorCount = 0;

        String newUsername = request.getParameter("newUsername");

        if (!validateUsername(newUsername)) {
            error.append("Username must contain one letter"
                    + "<br/>and be between 8 and 20 characters");
            errorCount = errorCount + 2;
        }

        if (!error.toString().equals("")) {
            request.setAttribute("changeUsernameError", error);
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
