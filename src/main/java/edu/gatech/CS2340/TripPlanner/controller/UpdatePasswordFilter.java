package main.java.edu.gatech.CS2340.TripPlanner.controller;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(
        filterName = "UpdatePasswordFilter",
        urlPatterns = {"/Account/updatePassword" }
)


public class UpdatePasswordFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
        throws IOException, ServletException {
        System.out.println("made it here");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/Account/settings.jsp");

        StringBuffer error = new StringBuffer("");

        int errorCount = 0;

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!validatePassword(newPassword)) {
            error.append("Password must contain at least"
                    + "<br/>one number, one uppercase "
                    + "<br/>letter, one lowercase letter,"
                    + "<br/>and should be between 8 and 20"
                    + "<br/>characters");
            errorCount = errorCount + 6;
        } else if (!newPassword.equals(confirmPassword)) {
            error.append("Passwords do not match");
            errorCount++;
        }

        if (!error.toString().equals("")) {
            request.setAttribute("changePasswordError", error);
            request.setAttribute("errorCount", Integer.toString(errorCount));
            dispatcher.forward(request, response);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

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
