package com.example.pangerlular;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private List<CartProduct> cartProducts;

    public Cart(int id, List<CartProduct> cartProducts) {
        this.id = id;
        this.cartProducts = cartProducts;
    }

    public Cart() {
        this.cartProducts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public void addProduct(CartProduct product){
        cartProducts.add(product);
    }

    public void removeProduct(CartProduct product){
        cartProducts.remove(product);
    }

    public CartProduct findProductWithName(String productName){
        for (CartProduct item :
                cartProducts) {
            if(item.getProduct().getName().toLowerCase().contains(productName.toLowerCase())){
                return item;
            }
        }
        return null;
    }
}
