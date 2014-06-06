package main.java.edu.gatech.cs2340.tripplanner.tripplanner.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Samarth on 6/5/2014.
 */

@WebServlet(urlPatterns={
        "/login"
        })

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        String error = "Couldn't find your ID!";

        request.setAttribute("error", error);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request,  response);

    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

    }
}
