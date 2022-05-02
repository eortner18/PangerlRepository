package com.example.pangerlular;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationBarView;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
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
    ProductAdapter proAdapter;
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager db = new DBManager();

        db.initProducts(new InputStreamReader(getResources().openRawResource(R.raw.products)));
        listView = findViewById(R.id.ListViewProducts);

        products = new ArrayList<>(db.getProducts());
        /*List<String> productsViewList = new ArrayList<>();
        for (Product product :
                products) {
            productsViewList.add(product.getName());
        }*/


        //setUpSpinner();


        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,productsViewList);
        //listView.setAdapter(arrayAdapter);

        //instantiate adapter
        //https://www.youtube.com/watch?v=k7KhHM3fCik&ab_channel=roottech
        proAdapter = new ProductAdapter(getApplicationContext(), R.layout.list_item, products);
        listView.setAdapter(proAdapter);


        searchViewListener();

        ImageButton cartButton = findViewById(R.id.ShoppingCartImageButton);
        cartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startCartActivity(view);
            }
        });

    }

    private void setUpSpinner()
    {
        Spinner catSpinner= findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    public void searchViewListener(){
        SearchView searchView  = findViewById(R.id.searchBarSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void startCartActivity(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }


    public void btnSettings_OnClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }
}