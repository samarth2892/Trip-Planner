package main.java.edu.gatech.CS2340.TripPlanner.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "forgotPasswordFilter",
        urlPatterns = {
                "/forgotPassword"
        }
)

public class forgotPasswordFilter implements Filter {

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
                request.getRequestDispatcher("forgotPassword.jsp");

        StringBuffer error = new StringBuffer("");

        int errorCount = 0;

        String username = request.getParameter("username");
        String email = request.getParameter("email");

        if (username.equals("")) {
            error.append("Please enter a username");
            errorCount++;
        }

        if (email.equals("")) {
            error.append("Please enter an email");
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
}