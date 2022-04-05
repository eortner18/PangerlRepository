package com.example.pangerlular;

import java.util.List;

public class Cart {
    private int id;
    private List<Product> cartProducts;

    public Cart(int id, List<Product> cartProducts) {
        this.id = id;
        this.cartProducts = cartProducts;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<Product> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public void addProduct(Product product){
        cartProducts.add(product);
    }

    public void removeProduct(Product product){
        cartProducts.remove(product);
    }

    public Product findProductWithName(String productName){
        for (Product item :
                cartProducts) {
            if(item.getName().toLowerCase().contains(productName.toLowerCase())){
                return item;
            }
        }
        return null;
    }
}
