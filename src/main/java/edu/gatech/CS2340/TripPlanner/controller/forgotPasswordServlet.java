package main.java.edu.gatech.CS2340.TripPlanner.controller;

import main.java.edu.gatech.CS2340.TripPlanner.model.AccountDb;
import main.java.edu.gatech.CS2340.TripPlanner.model.EmailSender;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

@WebServlet(urlPatterns = {
        "/forgotPassword"
        })
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
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String temPassword = createRandomPassword();

        if (database.userEmailMatch(username, email)) {
            matchConfirmation = "Temporary password sent to email";
            EmailSender.sendTempPassword(username, email, temPassword);
            database.resetPassword(username, temPassword);
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

    private String createRandomPassword() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
