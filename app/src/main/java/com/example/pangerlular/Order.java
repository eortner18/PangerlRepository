package com.example.pangerlular;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private List<Product> orderProducts;
    private Customer customer;
    private Date orderDate;

    public Order(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
        orderDate = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public List<Product> getOrderProducts() {
        return orderProducts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderProducts(List<Product> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderProducts=" + orderProducts +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                '}';
    }
}
