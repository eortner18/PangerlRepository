package com.example.pangerlular;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager db = new DBManager();
        db.initProducts(readProductsFromCsv());
        listView = findViewById(R.id.listView);

        db.addCustomer(new Customer(0, "Hans", "Peter", "hpeter", "12345", "h.@gmail", new Address("Erdbeerstrasse 2", 4070, "Eferding"), new Cart()));
        db.addCustomer(new Customer(1, "Franz", "Kunsti", "fkunsti", "12345", "f.@gmail", new Address("Erdbeerstrasse 2", 4070, "Eferding"), new Cart()));


        List<Customer> customers = db.getCustomers();
        List<Product> products = readProductsFromCsv();


    }

    private List<Product> readProductsFromCsv() {
        List<Product> products = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.products)));
            String[] nextLine;
            while((nextLine = reader.readNext()) != null)  {
                nextLine = nextLine[0].split(";");
                products.add( new Product( nextLine[0], nextLine[1], Double.parseDouble(nextLine[2]), nextLine[3]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception");
        }
        return products;
    }
}