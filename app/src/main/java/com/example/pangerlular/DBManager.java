package com.example.pangerlular;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pangerlulardb-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference("products");

    public void initProducts(List<Product> products){
        ref.setValue(products);
    }


}
