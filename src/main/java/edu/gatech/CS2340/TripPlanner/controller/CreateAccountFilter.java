package main.java.edu.gatech.CS2340.TripPlanner.controller;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        if (name.equals("")) error = "Please enter your Name";
        else if (username.equals("")) error = "Please enter a Username";
        else if (createPassword.equals("")) error = "Please create a password";
        else if (confirmPassword.equals("")) error = "Please confirm password";
        else if (!createPassword.equals(confirmPassword))
            error = "Passwords do not match";
        else  {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        request.setAttribute("error", error);
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
