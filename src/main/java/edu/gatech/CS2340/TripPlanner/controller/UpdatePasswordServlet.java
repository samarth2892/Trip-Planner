package main.java.edu.gatech.CS2340.TripPlanner.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import main.java.edu.gatech.CS2340.TripPlanner.model.AccountDb;

@WebServlet(urlPatterns = {
        "/Account/updatePassword"
}
)
public class UpdatePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher;
        AccountDb database = new AccountDb();
        database.connect();

        Object username = request.getSession().getAttribute("userStatus");
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");

        database.updatePassword((String) username, password, newPassword);

        String passwordChangedConfirmation = "Password changed";
        request.setAttribute("error", passwordChangedConfirmation);
        dispatcher = request.getRequestDispatcher("home.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}
