package com.example.androidstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidstore.application.HomeApplication;
import com.example.androidstore.dto.ProductDTO;
import com.example.androidstore.models.RegisterModel;
import com.example.androidstore.network.services.ProductService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Integer userId;
    HomeApplication application = HomeApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void TryLoadActivity() {
        if (application.chechUser() == true) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public boolean Validate(TextInputEditText email,
                            TextInputLayout emailLayout,
                            TextInputEditText password,
                            TextInputLayout passwordLayout) {
        boolean valid = true;

        if(email.getText().toString().isEmpty()) {
            emailLayout.setError("Не вказали емайл");
            valid = false;
        } else {
            emailLayout.setError(null);
        }

        if (password.getText().toString().isEmpty()) {
            passwordLayout.setError("Не вказали пароль");
            valid = false;
        } else {
            passwordLayout.setError(null);
        }

        return valid;
    }

    public void OnClickButtonRegister(View view) {
        final TextInputEditText email = findViewById(R.id.textInputEmail);
        final TextInputLayout emailLayout = findViewById(R.id.textFieldEmail);
        final TextInputLayout passwordLayout = findViewById(R.id.textFieldPassword);
        final TextInputEditText password = findViewById(R.id.textInputPassword);

        //Check if we already logged in and doesn't need to register
        TryLoadActivity();

        //Validate input by user fields
        if(!Validate(email, emailLayout, password, passwordLayout)) return;

        RegisterModel registerModel = new RegisterModel(email.getText().toString(), password.getText().toString());

        //Throwing request - register
        ProductService.getInstance()
                .getProductsApi()
                .register(registerModel)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (!response.isSuccessful()) {
                            emailLayout.setError("Введіть ваш емайл");
                            userId = null;
                        } else {
                            userId = response.body();
                            application.setUser(userId);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        userId = null;
                    }
                });

        if (userId == null) {
            emailLayout.setError("That username already taken");
        }
        else {
            application.setUser(userId);
        }

        TryLoadActivity();
    }
}