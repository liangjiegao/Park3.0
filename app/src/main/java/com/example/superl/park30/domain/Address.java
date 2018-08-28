package com.example.superl.park30.domain;

/**
 * Created by SuperL on 2018/8/21.
 */

public class Address {

    private String name;
    private String city;

    public Address(String name, String city){
        this.name = name;
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
