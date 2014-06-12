package main.java.edu.gatech.CS2340.TripPlanner.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import main.java.edu.gatech.CS2340.TripPlanner.model.AccountDb;

@WebServlet(urlPatterns={
        "/Create"
})

public class CreateAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher dispatcher;
        AccountDb database = new AccountDb();
        database.connect();

        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("createPassword");

        database.create(username, password, name);

        String accountConfirmation = "Account created, please login";
        request.setAttribute("error", accountConfirmation);
        dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}
