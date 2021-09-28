package com.example.androidstore.network;

import com.example.androidstore.dto.ProductDTO;
import com.example.androidstore.dto.ProductImageDTO;
import com.example.androidstore.models.RegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductApi {
    /*
     * =============
     *  GET Methods
     * =============
     */

    @GET("/api/Products/all")
    public Call<List<ProductDTO>> all();

    @GET("/api/Products/get/{id}")
    public Call<List<ProductImageDTO>> getPostWithID(@Path("id") int id);

    /*
     * =============
     *  POST Methods
     * =============
     */

    @POST("/api/Account/register")
    public Call<Integer> register(@Body RegisterModel registerModel);
}
