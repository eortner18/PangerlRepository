package com.example.pangerlular;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public class DBManager {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pangerlulardb-default-rtdb.europe-west1.firebasedatabase.app/");

    DatabaseReference productReference = database.getReference("products");
    DatabaseReference customerReference = database.getReference("customers");
    List<Customer> customers = new ArrayList<>();
    List<Product> products = new ArrayList<>();

    public DBManager() {
        productsDatabaseListener();
        customerDatabaseListener();


    }

    public void initProducts(List<Product> products) {
        productReference.setValue(products);
    }

    public List<Product> getProducts() {


        return products;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        customerReference.setValue(customers);
    }

    public List<Customer> getCustomers() {

        return customers;

    }

    public void productsDatabaseListener(){
        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    products.add(postSnapshot.getValue(Product.class));
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
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    customers.add(postSnapshot.getValue(Customer.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
