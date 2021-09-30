package com.example.androidstore.models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("email")
    public String Email;
    @SerializedName("phone")
    public String Phone;
    @SerializedName("password")
    public String Password;

    public String getEmail() {
        return Email;
    }

    public LoginModel(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPassword() {
        return Password;
    }
}
