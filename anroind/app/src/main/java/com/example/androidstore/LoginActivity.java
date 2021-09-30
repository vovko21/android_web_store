package com.example.androidstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidstore.application.HomeApplication;
import com.example.androidstore.models.LoginModel;
import com.example.androidstore.models.RegisterModel;
import com.example.androidstore.network.services.ProductService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private int userId;
    HomeApplication application = HomeApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void TryLoadActivity() {
        if (application.chechUser() == true) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void OnClickButtonLogin(View view) {
        final TextInputEditText email = findViewById(R.id.textInputEmail);

        //Validate input by user fields
        final TextInputLayout passwordLayout = findViewById(R.id.textFieldPassword);
        final TextInputEditText password = findViewById(R.id.textInputPassword);
        if (password.getText().toString().isEmpty()) {
            passwordLayout.setError("Не вказали пароль");
        } else {
            password.setError(null);
        }

        LoginModel loginModekl = new LoginModel(email.getText().toString(), password.getText().toString());

        //Throwing request - register
        ProductService.getInstance()
                .getProductsApi()
                .login(loginModekl)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        userId = response.body();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        userId = -1;
                    }
                });

        if(userId > 0) {
            application.setUser(userId);

            TryLoadActivity();
        }
    }

    public void OnClickButtonGoRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}