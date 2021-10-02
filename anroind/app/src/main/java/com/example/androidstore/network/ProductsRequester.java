package com.example.androidstore.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.androidstore.application.HomeApplication;
import com.example.androidstore.dto.ProductDTO;
import com.example.androidstore.dto.ProductImageDTO;
import com.example.androidstore.network.services.ProductService;
import com.example.androidstore.utils.LoaderUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsRequester {
    private static ProductsRequester instance = null;
    private final Context context;
    private final RequestQueue requestQueue;
    private List<ProductDTO> allProducts;

    private ProductsRequester() {
        context = HomeApplication.getAppContext();
        this.requestQueue = Volley.newRequestQueue(context);
        this.requestQueue.start();
        this.allProducts = new ArrayList<>();
    }

    public static ProductsRequester getInstance() {
        if (instance == null) {
            instance = new ProductsRequester();
        }
        return instance;
    }

    public List<ProductDTO> getAllProducts() {
        ProductService.getInstance()
                .getProductsApi()
                .all()
                .enqueue(new Callback<List<ProductDTO>>() {

                    @Override
                    public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                        allProducts = response.body();
                        LoaderUtils.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                        LoaderUtils.hideLoading();
                    }
                });

        return allProducts;
    }

    public void getImages(){
        ProductService.getInstance()
                .getProductsApi()
                .getPostWithID(1)
                .enqueue(new Callback<List<ProductImageDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProductImageDTO>> call, Response<List<ProductImageDTO>> response) {
                        List<ProductImageDTO> list = response.body();
                        String str = "";
                        for (ProductImageDTO item : list) {
                            str += item.getPath() + "\n";
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductImageDTO>> call, Throwable t) {

                }
        });
    }
}
