package com.example.pangerlular;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.InputMismatchException;

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


                    if (password.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        password.setTextColor(Color.RED);
                        return;
                    }
                    int lastCustomerId = getLastCustomerId();
                    String passwordHash = hash(password.getText().toString());


                    db.addCustomer(new Customer(lastCustomerId, vorname.getText().toString(), nachname.getText().toString(), passwordHash, email.getText().toString(),
                            new Address(strasse.getText().toString(), Integer.parseInt(plz.getText().toString()), ort.getText().toString()), new Cart()));
                    Toast.makeText(getApplicationContext(), "Registered successful", Toast.LENGTH_LONG).show();
                    startLoginActivity(view);
                }catch (NumberFormatException ex){
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
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
        if (db.getCustomers().size() > 1){
            lastCustomerId = db.getCustomers().stream().mapToInt(Customer::getId).max().getAsInt();
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


}