package com.example.pangerlular;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.text.DecimalFormat;
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
    CartProductAdapter cartProductAdapter;
    ListView cartProductsListView;
    Customer currentCustomer = LoginActivity.currentCustomer;
    DBManager db = new DBManager();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cart);




        cartProductsListView = findViewById(R.id.cartProductsView);
        Button bestellen = findViewById(R.id.bestellen);

        ImageButton settings = findViewById(R.id.btnSettings);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchViewListener();


        //region Objects for testing
        /*currentCustomer.setCart(new Cart());
        List<Product> testProducts = DBManager.products;
        for (int i = DBManager.products.size() -1; i > 6; i--) {
            currentCustomer.getCart().addProduct(new CartProduct((int) (Math.random() *10) +1,testProducts.get(i)));
        }

        db.updateCustomer(currentCustomer);*/

        //endregion


        setTotalPrice();
        setAdapter();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        bestellen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                builder.setTitle("Bestellen")
                        .setMessage("Wollen Sie alle Produkte aus Ihrem Warenkorb kostenpflichtig bestellen?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(isInternetAvailable()) {
                            sendEmail(currentCustomer.getCart().getCartProducts(), currentCustomer);
                            currentCustomer.setCart(new Cart());
                            db.updateCustomer(currentCustomer);
                            setTotalPrice();
                            setAdapter();
                        }else{
                            Toast.makeText(CartActivity.this, "Keine Internetverbindung. Bestellung wurde abgebrochen", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();


            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity(view);
            }
        });
    }

    private void setAdapter() {
        Button bestellen = findViewById(R.id.bestellen);
        cartProductAdapter = new CartProductAdapter(getApplicationContext(), R.layout.cart_product_item, currentCustomer.getCart().getCartProducts());
        cartProductAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                setTotalPrice();
                if(currentCustomer.getCart().getCartProducts().size() < 1 ){
                    bestellen.setEnabled(false);
                }
            }
        });
        cartProductsListView.setAdapter(cartProductAdapter);



        if(currentCustomer.getCart().getCartProducts().size() < 1 ){
            bestellen.setEnabled(false);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTotalPrice() {
        TextView gesamtpreis = findViewById(R.id.gesamtpreis);

        double sum = 0;

        for (CartProduct cartProduct :
                currentCustomer.getCart().getCartProducts()) {
            sum+= cartProduct.getAmount() * cartProduct.getProduct().getPrice() ;
        }

        gesamtpreis.setText(df.format(sum) + " €");
    }

    private void sendEmail(List<CartProduct> orderedProducts, Customer customer) {
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

            //region Build html for mail
            StringBuilder builder = new StringBuilder()
                    .append("<p><b><font>Rechnungsadresse</font></b><br/>")
                    .append("<font>" + customer.getFirstname() + " "+ customer.getLastname() + "</font><br/>")
                    .append("<font >" + customer.getAddress().getStreetNameNumber() +"</font><br/>")
                    .append("<font >" + customer.getAddress().getCity() + " " + customer.getAddress().getPostalcode() + "</font><br/>")
                    .append("<b><font>E-mail Adresse</font></b><br/>")
                    .append("<font >" + customer.getMail() + "</font></p><br/>")
                    .append("<center><font size=\"+1\"><b>Inhalt der Bestellung</b></font></center><br/>")


                    .append("<p><b><font>Bestellnummer "+ String.valueOf(currentCustomer.getId()) + String.valueOf((int) (Math.random() *1000) +1) +"</font></b></p>");

            double sum = 0;
            builder.append("<table cellspacing=\"22\">");
            for (CartProduct product:
                 orderedProducts) {
                sum+= (product.getProduct().getPrice() * product.getAmount());
                builder.append("<tr>");
                    builder.append("<td >" +product.getAmount() +"x </td>" +
                            "<td >" +product.getProduct().getName() +  "</td>" +
                            "<td >"+ df.format(product.getProduct().getPrice() * product.getAmount()) +"&euro;</td>");
                builder.append("</tr>");
            }
            builder.append("</table>");



            builder.append("<p text-align:\"right\">Zwischensumme: " +df.format(sum) +" &euro;</p>");
            builder.append("<p text-align:\"right\"><font size=\"+1\"><b>Summe: " +df.format(sum) +" &euro;</b></font></p><br/>");
            builder.append("<center><font size=\"+1\"><b>Vielen Dank für Ihre Bestellung</b></font></center>");

            builder.append("<br/><p><b>Zahlungsmethode</b><br/>");
            builder.append("Bar vor Ort</p><br/>");
            builder.append("<p><b>Lieferung und Zahlung</b><br/>Lieferung ist nach einer gesonderten Abhol-Email abzuholen bei <br/> Pangerl Obst-Gemüse-Südfrüchte GmbH <br/> Au bei hohen Steg 5 <br/> 4070 Eferding</p>");
            builder.append("<p><b>Haben Sie Fragen zu Ihrer Bestellung?</b><br/>Bei Fragen zur Bestellung, wenden Sie sich bei uns unter<br/> Pangerl Obst-Gemüse-Südfrüchte GmbH <br/> Au bei hohen Steg 5 <br/> 4070 Eferding <br/> Telefon: +43 7272 2453 <br/>E-mail: obst-pangerl.sta.io<br/><b>Öffnungszeiten</b> <br/> Montag bis Freitag von 8:00 bis 17:00 Uhr <br/> Samstag von 8:00 bis 12:00 Uhr</p>");

            String sendingText = builder.toString();
            //endregion


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(currentCustomer.getMail()));
            message.setSubject("Your Order was successful");
            message.setContent(sendingText, "text/html");
            Transport.send(message);

            Toast.makeText(CartActivity.this, "Bestellbestätigung wurde per Mail zugestellt",Toast.LENGTH_LONG).show();
        }catch(MessagingException e){
            Toast.makeText(CartActivity.this, "Fehler beim senden der Email. Produkte wurden nicht bestellt", Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    private void searchViewListener(){
        SearchView searchView  = findViewById(R.id.searchBarSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                cartProductAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void startSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}