package com.example.androidstore.dto;

import android.os.Parcel;

import lombok.Data;

@Data
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
}
