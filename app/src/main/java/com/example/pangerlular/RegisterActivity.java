package com.example.pangerlular;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
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
import java.util.InputMismatchException;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class RegisterActivity extends AppCompatActivity {

    DBManager db = new DBManager();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        EditText vorname = findViewById(R.id.txt_vorname);
        EditText nachname = findViewById(R.id.txt_nachname);
        EditText email = findViewById(R.id.txt_email);
        EditText password = findViewById(R.id.txt_password);
        EditText strasse = findViewById(R.id.txt_strasse);
        EditText plz = findViewById(R.id.txt_plz);
        EditText ort = findViewById(R.id.txt_ort);
        Button registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!isInternetAvailable()){
                        Toast.makeText(RegisterActivity.this, "Keine Internetverbindung", Toast.LENGTH_LONG).show();
                        return;
                    }
                    boolean validInputs = true;

                    if(password.getText().toString().equals("")){
                        password.setTextColor(Color.RED);
                        validInputs = false;
                    }
                    if(vorname.getText().toString().equals("")){
                        vorname.setTextColor(Color.RED);
                        validInputs = false;
                    }
                    if(nachname.getText().toString().equals("")){
                        nachname.setTextColor(Color.RED);
                        validInputs = false;
                    }
                    if(email.getText().toString().equals("") || !isValidEmailAddress(email.getText().toString())){
                        email.setTextColor(Color.RED);
                        validInputs = false;
                    }
                    if(strasse.getText().toString().equals("")){
                        strasse.setTextColor(Color.RED);
                        validInputs = false;
                    }
                    if(plz.getText().toString().equals("")){
                        plz.setTextColor(Color.RED);
                        validInputs = false;
                    }
                    if(ort.getText().toString().equals("")){
                        ort.setTextColor(Color.RED);
                        validInputs = false;
                    }

                    if(validInputs) {
                        int lastCustomerId = getLastCustomerId();
                        String passwordHash = hash(password.getText().toString());

                        db.addCustomer(new Customer(lastCustomerId, vorname.getText().toString(), nachname.getText().toString(), passwordHash, email.getText().toString(),
                                new Address(strasse.getText().toString(), Integer.parseInt(plz.getText().toString()), ort.getText().toString()), new Cart()));
                        Toast.makeText(getApplicationContext(), "Registered successful", Toast.LENGTH_LONG).show();
                        startLoginActivity(view);
                    }else{
                        Toast.makeText(getApplicationContext(), "Missing or wrong inputs", Toast.LENGTH_LONG).show();
                    }
                }catch (NumberFormatException ex){
                    Toast.makeText(getApplicationContext(), "Wrong input for PLZ", Toast.LENGTH_LONG).show();
                    plz.setTextColor(Color.RED);
                }

            }
        });


    }



    public void startLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getLastCustomerId(){
        int lastCustomerId = 0;
        if (DBManager.customers.size() >= 1){
            lastCustomerId = DBManager.customers.stream().mapToInt(Customer::getId).max().getAsInt() +1;
        }
        return lastCustomerId;
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


    public boolean isValidEmailAddress(String email)
    {
        for (Customer customer :
                DBManager.customers) {
            if (customer.getMail().equals(email)) {
                Toast.makeText(getApplicationContext(), "Email is already being used", Toast.LENGTH_LONG).show();
                return false;
            }
        }


        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}