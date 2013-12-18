package fengfei.spruce.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import java.net.MalformedURLException;
import java.net.URL;

public class Mail {

    public static void main(String args[]) {
        Base64 base64 = new Base64();

        String str = "中文";
        byte[] enbytes = null;
        String encodeStr = null;
        byte[] debytes = null;
        String decodeStr = null;

        enbytes = base64.encodeBase64(str.getBytes(), false);
        encodeStr = new String(enbytes);
        encodeStr.replaceAll("[\n\r]", "");
        debytes = base64.decode(enbytes);
        decodeStr = new String(debytes);

        System.out.println("编码前:" + str);
        System.out.println("编码后:" + encodeStr);
        System.out.println("解码后:" + decodeStr);
    }

    public void sendSimple() throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("username", "password"));
        email.setSSL(true);
        email.setFrom("user@gmail.com");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("foo@bar.com");
        email.send();
    }

    public void sendHTML() throws EmailException, MalformedURLException {
        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setHostName("mail.myserver.com");
        email.addTo("jdoe@somewhere.org", "John Doe");
        email.setFrom("me@apache.org", "Me");
        email.setSubject("Test email with inline image");

        // embed the image and get the content id
        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        String cid = email.embed(url, "Apache logo");

        // set the html message
        email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // send the email
        email.send();
    }
}
