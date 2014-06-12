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
        "/LogOut",
        "/LogIn",
})

public class LoginLogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        AccountDb database = new AccountDb();
        database.connect();

        RequestDispatcher dispatcher;

        String error;
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(database.login(username, password)){
            request.getSession().setAttribute("userStatus", username);
            dispatcher = request.getRequestDispatcher("/Account/home.jsp");
            dispatcher.forward(request, response);
            return;
        } else {
            error = "Username or password incorrect";
            request.setAttribute("error", error);
            dispatcher = request.getRequestDispatcher("index.jsp");
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
