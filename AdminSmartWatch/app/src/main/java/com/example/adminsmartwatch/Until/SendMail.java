//package com.example.adminsmartwatch.Until;
//
//import android.os.StrictMode;
//
//import java.util.Properties;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class SendMail {
//    public static void guiMail_donHang(){
//        final String userName = "dungro1123vn@gmail.com";
//        final String password = "Comosellama0922967024";
//        Properties props = new Properties();
//        props.put("mail.smtp.auth","true");
//        props.put("mail.smtp.starttls.enable","true");
//        props.put("mail.smtp.host","smtp.gmail.com");
//        props.put("mail.smtp.port","578");
//
//        Session session = Session.getInstance(props,
//                new Authenticator() {
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(userName,password);
//                    }
//                });
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(userName));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("hoanggiang15029x@gmail.com"));
//            message.setSubject("Sending email without opening gmail apps");
//            message.setText("hello");
//            Transport.send(message);
//        }catch (MessagingException e){
//            throw new RuntimeException(e);
//        }
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//    }
//}
