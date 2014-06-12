package main.java.edu.gatech.CS2340.TripPlanner.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import main.java.edu.gatech.CS2340.TripPlanner.model.AccountDb;

public class UpdateUsernameServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher;
        AccountDb database = new AccountDb();
        database.connect();

        Object username = request.getSession().getAttribute("userStatus");
        String newUsername = request.getParameter("newUsername");
        String password = request.getParameter("password");

        database.updateUsername((String) username , password, newUsername);

        String usernameChangedConfirmation = "Username changed";
        request.setAttribute("error", usernameChangedConfirmation);
        dispatcher = request.getRequestDispatcher("home.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}
