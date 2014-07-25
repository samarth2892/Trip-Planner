package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.AccountDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {
        "/forgotPassword"
}
)
public class forgotPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher
                = request.getRequestDispatcher("/forgotPassword.jsp");
        AccountDb database = new AccountDb();
        database.connect();

        String matchConfirmation;
        String username = request.getParameter("useraname");
        String email = request.getParameter("email");

        if (database.userEmailMatch(username, email)) {
            matchConfirmation = "Username and email match";
        } else {
            matchConfirmation = "Username and email do not match";
        }
        request.setAttribute("errorCount", Integer.toString(1));
        request.setAttribute("forgotPasswordError",
                matchConfirmation);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}
