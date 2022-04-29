package com.example.pangerlular;

public class CartProduct {
    private int amount;
    private Product product;


    public CartProduct( int amount, Product product) {

        this.amount = amount;
        this.product = product;

    }

    public CartProduct(){

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "amount=" + amount +
                ", product=" + product +
                '}';
    }
}
