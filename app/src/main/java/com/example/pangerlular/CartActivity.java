package com.example.pangerlular;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CartActivity extends AppCompatActivity {


    Customer currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button button = findViewById(R.id.buttonId);


        //just for testing reasons
        //real currentUser will be implemented soon
        Cart testCart = new Cart();
        testCart.addProduct(new CartProduct(3, new Product( "Kacke",  "FRUIT",12.0, "")));
        testCart.addProduct(new CartProduct(5,new Product("Pisse",  "FRUIT",100.0, "")));
        currentUser = new Customer(0, "Hans", "Peter", "hpeter", "12345", "elias.reiter2nd@gmail.com", new Address("Erdbeerstrasse 2", 4070, "Eferding"), testCart);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendEmail(currentUser.getCart().getCartProducts(), currentUser);

            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    protected void sendEmail(List<CartProduct> orderedProducts, Customer customer) {
        Log.i("Send email", "");

        final String username= "pangerl.orders@gmail.com";
        final String password = "pangerlular%1";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try{

            StringBuilder builder = new StringBuilder()
                    .append("<p><b><font>Rechnungsadresse</font></b><br/>")
                    .append("<font>" + customer.getFirstname() + " "+ customer.getLastname() + "</font><br/>")
                    .append("<font >" + customer.getAddress().getStreetNameNumber() +"</font><br/>")
                    .append("<font >" + customer.getAddress().getCity() + " " + customer.getAddress().getPostalcode() + "</font><br/>")
                    .append("<b><font>E-mail Adresse</font></b><br/>")
                    .append("<font >" + customer.getMail() + "</font></p><br/>")
                    .append("<center>Inhalt der Bestellung</center><br/>")
                    .append("<p><b><font>Bestellnummer xxxx</font></b></p>");
            double sum = 0;
            for (CartProduct product:
                 orderedProducts) {
                sum+= product.getProduct().getPrice();
                    builder.append("<div>" +product.getAmount() +"x &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;" +product.getProduct().getName() +  "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+product.getProduct().getPrice() +"</div>");
            }
            builder.append("<br/><p><b>Zahlungsmethode</b><br/>");
            builder.append("Bar vor Ort</p><br/>");


            builder.append("<p text-align:\"right\">Zwischensumme :" +sum +" &euro;</p>");
            builder.append("<p text-align:\"right\"><font size=\"+1\"><b>Summe: " +sum +" &euro;</b></font></p>");
            String sendingText = builder.toString();



            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(currentUser.getMail()));
            message.setSubject("Your Order was successful");
            message.setContent(sendingText, "text/html");
            Transport.send(message);
            Toast.makeText(getApplicationContext(), "email sent successfully",Toast.LENGTH_LONG).show();
        }catch(MessagingException e){
            throw new RuntimeException(e);
        }
    }
}