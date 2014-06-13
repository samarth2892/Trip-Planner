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
        "/Account/updatePassword" }
)
public class UpdatePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
        throws IOException, ServletException {

        RequestDispatcher dispatcher
            = request.getRequestDispatcher("/Account/settings.jsp");
        AccountDb database = new AccountDb();
        database.connect();

        String passwordChangedConfirmation;
        String username
            = (String) request.getSession().getAttribute("userStatus");
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");

        if (database.login(username, password)) {
            database.updatePassword(username, password, newPassword);
            passwordChangedConfirmation = "Password changed";
        } else {
            passwordChangedConfirmation = "Current password doesn't match";
        }
        request.setAttribute("errorCount", Integer.toString(1));
        request.setAttribute("changePasswordError",
                passwordChangedConfirmation);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
        throws IOException, ServletException {
    }
}
