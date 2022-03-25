package com.example.pangerlular;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager db = new DBManager();
        db.initProducts(productsFromCsv());

    }

    private List<Product>  productsFromCsv() {
        List<Product> products = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(R.raw.products)));
            String[] nextLine;
            while((nextLine = reader.readNext()) != null)  {
                nextLine = nextLine[0].split(";");
                products.add( new Product( nextLine[0], ETypen.valueOf(nextLine[1]), Double.parseDouble(nextLine[2])));
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