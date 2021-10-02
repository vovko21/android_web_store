package com.example.androidstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.androidstore.application.HomeApplication;
import com.example.androidstore.application.helpers.ImageDownloader;
import com.example.androidstore.dto.ProductDTO;
import com.example.androidstore.models.RegisterModel;
import com.example.androidstore.network.services.ProductService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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

    public void onClickLoadImage(View view) {
        Intent picker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picker, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.imageView);

            Bitmap bitmap = getBitmapFromURL(selectedImage.getPath());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}