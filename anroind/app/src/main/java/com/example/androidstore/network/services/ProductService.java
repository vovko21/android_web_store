package com.example.androidstore.network.services;

import com.example.androidstore.constans.Urls;
import com.example.androidstore.network.ProductApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductService {
    private static ProductService mInstance;
    private static final String BASE_URL = Urls.BASE;
    private Retrofit mRetrofit;

    private ProductService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ProductService getInstance() {
        if (mInstance == null) {
            mInstance = new ProductService();
        }
        return mInstance;
    }

    public ProductApi getProductsApi() {
        return mRetrofit.create(ProductApi.class);
    }
}
