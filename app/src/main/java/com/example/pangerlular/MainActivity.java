package com.example.pangerlular;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager db = new DBManager();

        db.initProducts(new InputStreamReader(getResources().openRawResource(R.raw.products)));
        listView = findViewById(R.id.ListViewProducts);

        db.addCustomer(new Customer(0, "Hans", "Peter", "hpeter", "12345", "h.@gmail", new Address("Erdbeerstrasse 2", 4070, "Eferding"), new Cart()));
        db.addCustomer(new Customer(1, "Franz", "Kunsti", "fkunsti", "12345", "f.@gmail", new Address("Erdbeerstrasse 2", 4070, "Eferding"), new Cart()));


        List<Customer> customers = db.getCustomers();
        List<Product> products = db.getProducts();

        List<String> productsViewList = new ArrayList<>();
        for (Product product :
                products) {
            productsViewList.add(product.getName());
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,productsViewList);
        listView.setAdapter(arrayAdapter);


    }


}