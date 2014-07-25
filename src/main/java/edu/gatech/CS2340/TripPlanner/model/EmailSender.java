package main.java.edu.gatech.CS2340.TripPlanner.model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void main(String [] args) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "sending tests";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("gina3yu@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("gina3yu@gmail.com"));
            msg.setSubject("Your Example.com account has been activated");
            msg.setText(msgBody);
            Transport.send(msg);
            System.out.println("message sent successfully");

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


}

