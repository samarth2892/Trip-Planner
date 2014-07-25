package main.java.edu.gatech.CS2340.TripPlanner.model;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {

    private final static String ID = "tripplannercs2340@gmail.com";
    private final static String PASS = "Planner2340";

    private EmailSender(){}

    public static void sendTempPassword(String username, String email, String TempPassword) {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.auth", "true");
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ID,PASS);
            }
        };

        Session session = Session.getDefaultInstance(props, authenticator);

        String msgBody = "This is your temporary Password is <b>" + TempPassword + "</b>";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(ID, "TripPlanner Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email, username));
            msg.setSubject("Temporary Password");
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            System.out.println(e.toString());
        } catch (MessagingException e) {
            System.out.println(e.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.toString());
        }

    }

}

