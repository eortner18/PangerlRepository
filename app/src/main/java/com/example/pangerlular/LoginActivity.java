package com.example.pangerlular;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static Customer currentCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        DBManager db = new DBManager();



        Button loginButton = findViewById(R.id.loginbutton);
        Button registerButton = findViewById(R.id.registerbutton);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(view);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if(isInternetAvailable()) {
                    String hashedPassword = hash(password.getText().toString());

                    Customer loggedInCustomer = findCustomerWithPassword(email.getText().toString(), hashedPassword);

                    if (loggedInCustomer != null) {
                        currentCustomer = loggedInCustomer;
                        startMainActivity(view);
                    } else {
                        Toast.makeText(getApplicationContext(), "Email doesn't match password", Toast.LENGTH_LONG).show();
                        email.setTextColor(Color.RED);
                        password.setTextColor(Color.RED);
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Keine Internetverbindung", Toast.LENGTH_LONG).show();
                }

            }
        });

        TextView link = findViewById(R.id.linkView);
        link.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Uri uri = Uri.parse("https://online.wkooe.at/agb-shop"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    public void startRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }

    public void startMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String hash(String hashString){

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    hashString.getBytes(StandardCharsets.UTF_8));
            hashString = Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashString;
    }

    public Customer findCustomerWithPassword(String email, String hashedPassword){

        Customer erg = null;


        for (Customer customer:
             DBManager.customers) {
            if(customer.getMail().equals(email)){
                if(customer.getPassword().equals(hashedPassword)){
                    erg = customer;
                }
            }
        }

        return erg;
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}