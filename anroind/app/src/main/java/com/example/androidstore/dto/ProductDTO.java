package com.example.androidstore.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductDTO  {
    private String name;
    private double price;
    private String image;

    public ProductDTO() { }

    public ProductDTO(String name, double price) {
        this.name = name;
        this.price = price;
    }

    protected ProductDTO(Parcel in) {
        name = in.readString();
        price = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductImage() {
        return this.image;
    }
}
