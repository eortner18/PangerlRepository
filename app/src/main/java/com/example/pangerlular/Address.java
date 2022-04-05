package com.example.pangerlular;

public class Address {
    private String streetNameNumber;
    private int postalcode;
    private String city;

    public Address(String streetNameNumber, int postalcode, String city) {
        this.streetNameNumber = streetNameNumber;
        this.postalcode = postalcode;
        this.city = city;
    }

    public Address() {
    }

    public String getStreetNameNumber() {
        return streetNameNumber;
    }

    public void setStreetNameNumber(String streetNameNumber) {
        this.streetNameNumber = streetNameNumber;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetNameNumber='" + streetNameNumber + '\'' +
                ", postalcode=" + postalcode +
                ", city='" + city + '\'' +
                '}';
    }
}
