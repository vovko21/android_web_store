package com.example.androidstore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.example.androidstore.dto.ProductDTO;
import com.example.androidstore.network.ProductsRequester;
import com.google.android.material.textfield.TextInputEditText;
import com.oginotihiro.cropview.CropView;

import java.io.ByteArrayOutputStream;

public class AddProductActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    AddProductActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        activity = this;
    }

    public void OnClickButtonCreateProduct(View view) {
        TextInputEditText name = findViewById(R.id.textInputName);
        TextInputEditText price = findViewById(R.id.textInputPrice);

        //Image
        CropView cropView = (CropView) findViewById(R.id.cropView);
        Bitmap croppedBitmap = cropView.getOutput();
        Matrix matrix = new Matrix();

        matrix.postRotate(cropView.getRotation());
        Bitmap rotatedBitmap = Bitmap.createBitmap(croppedBitmap, 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight(), matrix, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        ProductDTO productDTO = new ProductDTO(name.getText().toString(), Double.parseDouble(price.getText().toString()), encoded);

        ProductsRequester.getInstance().addProduct(productDTO);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        //CommonUtils.showLoading(this);
                        Uri selectedImage = data.getData();
                        CropView cropView = (CropView) findViewById(R.id.cropView);
                        cropView.of(selectedImage)
                                //.withAspect(x, y)
                                .withOutputSize(100, 100)
                                .initialize(activity);
                        //CommonUtils.hideLoading();
                    }

                }
            });

    public void openSomeActivityForResult(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Intent intent = new Intent(this, SomeActivity.class);
        someActivityResultLauncher.launch(i);
    }

    public void OnClickButtonCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}