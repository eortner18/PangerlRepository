package com.example.pangerlular;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Product {


    private String name;
    private String type;
    private double price;
    private String productImageURL;

    public Product( String name, String type, double price, String productImageURL) {

        this.name = name;
        this.type = type;
        this.price = price;
        this.productImageURL = productImageURL;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", productImageURL='" + productImageURL + '\'' +
                '}';
    }
}
