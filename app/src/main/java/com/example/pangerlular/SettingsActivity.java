package com.example.pangerlular;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    boolean isDark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        DBManager db = new DBManager();

        Button deleteAccount = findViewById(R.id.deleteAccount);
        Button switch_btn = findViewById(R.id.switch_btn);
            switch_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isDark == false) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        isDark = true;
                    }
                    else if (isDark == true) {

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        isDark = false;

                    }
                }
            });




            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            deleteAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.setTitle("Account Löschen")
                            .setMessage("Wollen Sie Ihren Account unwiederruflich löschen?")
                            .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db.deleteCustomer(LoginActivity.currentCustomer);
                                    restartFromLoginActivity(view);
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


        }


    public void restartFromLoginActivity(View view) {
        LoginActivity.currentCustomer = null;
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

}


