package com.murun.fict.model;



import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="ADDRESS")
public class Address implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_ID")
    private int addressId;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP_CODE")
    private String zipCode;


    public Address() {
    }

    public int getAddressId() {
        return addressId;
    }


    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }



    public void setStreet(String street) {
        this.street = street;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public void setState(String state) {
        this.state = state;
    }


    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }


}
