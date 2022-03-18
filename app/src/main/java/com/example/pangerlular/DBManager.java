package com.example.pangerlular;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManager {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pangerlulardb-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference("products");
    public void writeToDatabase(){



        List<Product> products = new ArrayList<>();

        products.add( new Product( "Apfel", null, 1.50));
        products.add( new Product( "Bananen", null, 2));

        ref.setValue(products);



    }


}
