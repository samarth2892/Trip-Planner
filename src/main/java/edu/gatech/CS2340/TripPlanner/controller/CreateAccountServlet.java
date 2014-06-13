package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.AccountDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        String accountConfirmation = "";
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("createPassword");

        if(!database.usernameIsInUse(username)) {
            database.create(username, password, name);
            accountConfirmation = "Account created, please login";
            dispatcher = request.getRequestDispatcher("index.jsp");
        } else {
            accountConfirmation = "Please choose a different Username";
            request.setAttribute("errorCount", Integer.toString(2));
            dispatcher = request.getRequestDispatcher("signUp.jsp");
        }
        request.setAttribute("error", accountConfirmation);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    }
}
