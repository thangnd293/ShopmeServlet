package com.api.model.bill;

import org.bson.types.ObjectId;

public class ShippingAddressModel {
    private ObjectId id;
    private String city;
    private String state;
    private String postalCode;
    private String countryCode;

    public ShippingAddressModel(ObjectId id, String city, String state, String postalCode, String countryCode) {
        this.id = id;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
    }

    public ShippingAddressModel() {};

    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
