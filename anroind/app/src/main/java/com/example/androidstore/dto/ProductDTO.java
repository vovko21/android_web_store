package com.example.androidstore.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductDTO implements Parcelable {
    private String name;
    private double price;

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

    public static final Creator<ProductDTO> CREATOR = new Creator<ProductDTO>() {
        @Override
        public ProductDTO createFromParcel(Parcel source) {
            String name = source.readString();
            double price = source.readDouble();
            return new ProductDTO(name, price);
        }

        @Override
        public ProductDTO[] newArray(int size) {
            return new ProductDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(price);
    }
}
