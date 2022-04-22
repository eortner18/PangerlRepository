package com.example.pangerlular;


import java.util.Objects;

public class Customer {
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String mail;
    private Address address;
    private Cart cart;
    private boolean isAdmin;

    public Customer(int id, String firstname, String lastname, String username, String password, String mail, Address address, Cart cart) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.address = address;
        this.cart = cart;
    }
    public Customer() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getPassword().equals(customer.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", address=" + address +
                ", cart=" + cart +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
