package com.example.pangerlular;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pangerlulardb-default-rtdb.europe-west1.firebasedatabase.app/");

    DatabaseReference productReference = database.getReference("products");
    DatabaseReference customerReference = database.getReference("customers");
    public static List<Customer> customers = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();

    public DBManager() {

        customerDatabaseListener();
        productsDatabaseListener();

    }

    public void initProducts(InputStreamReader inputStreamReader) {
        products.clear();
        try {
            CSVReader reader = new CSVReader(inputStreamReader);
            String[] nextLine;
            while((nextLine = reader.readNext()) != null)  {
                if(!nextLine[0].equals("")) {
                    String[] infoArray = nextLine[0].split(";");

                    products.add(new Product(infoArray[0], infoArray[1], Double.parseDouble(infoArray[2]), infoArray[3]));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception");
        }
        productReference.setValue(products);
    }




    public void addCustomer(Customer customer) {
        customers.add(customer);
        customerReference.setValue(customers);
    }

    public void resetCustomerInDatabase(Customer customer){
        for (int i = 0; i < customers.size(); i++) {
            if(customers.get(i).getMail().equals(customer.getMail())){
                customers.set(i, customer);
            }
        }
        customerReference.setValue(customers);
    }




    public void productsDatabaseListener(){
        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    products.add(postSnapshot.getValue(Product.class));
                    System.out.println("Product Listener triggered");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void customerDatabaseListener(){
        customerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customers.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    customers.add(postSnapshot.getValue(Customer.class));
                    System.out.println("Customer Listener triggered");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
