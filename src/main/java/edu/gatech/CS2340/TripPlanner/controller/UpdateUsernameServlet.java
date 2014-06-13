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
        "/Account/updateUsername"
        }
)
public class UpdateUsernameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Account/settings.jsp");
        AccountDb database = new AccountDb();
        database.connect();

        String usernameChangedConfirmation;
        String username = (String) request.getSession().getAttribute("userStatus");
        String newUsername = request.getParameter("newUsername");
        String password = request.getParameter("password");

        if (!database.login(username, password)) {
            usernameChangedConfirmation = "Current password doesn't match";
        } else if (database.usernameIsInUse(newUsername)) {
            usernameChangedConfirmation = "Please choose a different Username";
        } else {
            database.updateUsername(username, password, newUsername);
            request.getSession().setAttribute("userStatus",newUsername);
            usernameChangedConfirmation = "Username Changed";
         }
        request.setAttribute("errorCount", Integer.toString(1));
        request.setAttribute("changeUsernameError", usernameChangedConfirmation);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}
