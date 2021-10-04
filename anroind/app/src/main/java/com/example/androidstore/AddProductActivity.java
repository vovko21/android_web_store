package com.example.androidstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidstore.dto.ProductDTO;
import com.example.androidstore.network.ProductsRequester;
import com.google.android.material.textfield.TextInputEditText;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    public void OnClickButtonCreateProduct(View view) {
        TextInputEditText name = findViewById(R.id.textInputName);
        TextInputEditText price = findViewById(R.id.textInputPrice);

        ProductDTO productDTO = new ProductDTO(name.getText().toString(), Double.parseDouble(price.getText().toString()));

        ProductsRequester.getInstance().addProduct(productDTO);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void OnClickButtonCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}