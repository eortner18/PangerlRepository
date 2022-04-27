package com.example.pangerlular;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RegisterActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DBManager db = new DBManager();




        String passwordHash = hash("");


        int lastCustomerId = 0;
        if (db.getCustomers().size() > 1){
            lastCustomerId = db.getCustomers().stream().mapToInt(Customer::getId).max().getAsInt();
        }



        db.addCustomer(new Customer(lastCustomerId, "Hans", "Peter", "hpeter", passwordHash, "h.@gmail",
                new Address("Erdbeerstrasse 2", 4070, "Eferding"), new Cart()));

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


}