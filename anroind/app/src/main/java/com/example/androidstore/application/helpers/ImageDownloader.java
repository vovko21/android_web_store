package com.example.androidstore.application.helpers;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.androidstore.RegisterActivity;
import com.example.androidstore.application.HomeApplication;

import java.io.InputStream;
import java.net.URL;

public class ImageDownloader extends AsyncTask {

    ImageView image;
    ProgressDialog mProgressDialog;

    public ImageDownloader(ImageView image) {
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(Object... URL) {
        String imageURL = (String) URL[0];
        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(HomeApplication.getAppContext());
        mProgressDialog.setTitle("Downloading Image");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        Bitmap result = (Bitmap)o;
        // Set the bitmap into ImageView
        image.setImageBitmap(result);
        // Close progressdialog
        mProgressDialog.dismiss();
    }
}
