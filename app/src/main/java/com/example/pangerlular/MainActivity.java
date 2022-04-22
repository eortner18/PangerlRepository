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

        //final ListView list = findViewById(R.id.list);
        //ArrayList<SubjectData> arrayList = new ArrayList<SubjectData>();
     /* arrayList.add(new SubjectData("JAVA", "https://www.tutorialspoint.com/java/images/java-mini-logo.jpg"));
      arrayList.add(new SubjectData("Python","https://www.tutorialspoint.com/python/images/python-mini.jpg"));
      arrayList.add(new SubjectData("Javascript", "https://www.tutorialspoint.com/javascript/images/javascript-mini-logo.jpg"));
      arrayList.add(new SubjectData("Cprogramming",  "https://www.tutorialspoint.com/cprogramming/images/c-mini-logo.jpg"));
      arrayList.add(new SubjectData("Cplusplus", "https://www.tutorialspoint.com/cplusplus/images/cpp-mini-logo.jpg"));
      arrayList.add(new SubjectData("Android", "https://www.tutorialspoint.com/android/images/android-mini-logo.jpg"));

      */
        //ListViewAdapter listViewAdapter = new ListViewAdapter(this, arrayList);
        // list.setAdapter(listViewAdapter);

    }


}