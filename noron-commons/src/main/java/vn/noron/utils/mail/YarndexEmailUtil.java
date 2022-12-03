package vn.noron.utils.mail;


import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class YarndexEmailUtil {
    public static void main(String[] args) throws MessagingException {
        sendFromYandexNoreply("bacspm@gmail.com", "Mã xác thực quên mật khẩu tài khoản cuongnguyenba94@gmail.com của bạn tại https://noron.vn", "(NORON.VN) 291046 la ma xac thuc quen mat khau cua ban tai https://noron.vn. Thoi gian hieu luc: 5 phut");
    }
    public static void sendFromYandexNoreply(String to, String subject, String body) throws MessagingException {
        String fromEmail = "noreply.alert.tc2@mhsolution.vn";
        String password = "Mh#@!123";
        // Allow less secure apps => chu y
//        String fromEmail = "noreply.noron@gmail.com";
//        String password = "N0rOn@2021";
        sendFromYandex(to, subject, body, fromEmail, password);
    }
    public static void sendFromYandexSender(String to, String subject, String body) throws MessagingException {
//        String fromEmail = "sender@mhsolution.vn"; //requires valid gmail id
//        String password = "123456aA@"; // correct password for gmail id
        String fromEmail = "noreply.alert.tc2@mhsolution.vn";
        String password = "Mh#@!123";
        sendFromYandex(to, subject, body, fromEmail, password);
    }
    public static void sendFromYandex(String to, String subject, String body, String fromEmail, String password) throws MessagingException {
        Properties props = System.getProperties();
        String host = "smtp.yandex.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", fromEmail);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.quitwait", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setHeader("Content-Type", "text/plain; charset=UTF-8");
        try {
            message.setFrom(new InternetAddress(fromEmail));
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            message.setSentDate(new Date());
            Transport transport = session.getTransport("smtp");
            transport.connect(host, fromEmail, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ignored) {
            log.error("[SEND-MAIL] cause: {}", ignored);/*1651071067-66kUSJFRhH-p6MOVkoJ*/
        }
    }
    public static void sendFromGmail(String to, String subject, String body, String fromEmail, String password) throws MessagingException {
        Properties props = System.getProperties();
//        String host = "smtp.yandex.com";
        String host = "smtp.gmail.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", fromEmail);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.quitwait", "false");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setHeader("Content-Type", "text/plain; charset=UTF-8");
        try {
            message.setFrom(new InternetAddress(fromEmail));
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            message.setSentDate(new Date());
            Transport transport = session.getTransport("smtp");
            transport.connect(host, fromEmail, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ignored) {
            log.error("[SEND-MAIL] cause: {}", ignored);/*1651071067-66kUSJFRhH-p6MOVkoJ*/
        }
    }
}
