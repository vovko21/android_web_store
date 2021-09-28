package com.example.androidstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.androidstore.application.adapter.ProductAdapter;
import com.example.androidstore.constans.Urls;
import com.example.androidstore.dto.ProductDTO;
import com.example.androidstore.dto.ProductImageDTO;
import com.example.androidstore.network.ImageRequester;
import com.example.androidstore.network.services.ProductService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<ProductDTO> products = new ArrayList<ProductDTO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Loading all necessary data from Database;
        LoadFromDB();

        //Event listener on changing fragment
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,
                    new MainFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.mainFragment:
                            selectedFragment = new MainFragment();
                            break;
                        case R.id.listFragment:
//                            if(products == null) break;
                            selectedFragment = new ListFragment(products);
                            break;
                        default:
                            selectedFragment = new MainFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,
                            selectedFragment).commit();

                    return true;
            }
    };

    private void LoadFromDB() {
        //String url = Urls.BASE + "/images/manul.jpg";
        //imageRequester = ImageRequester.getInstance();
        //myImage = findViewById(R.id.myimg);
        //imageRequester.setImageFromUrl(myImage, url);

        //txtinfo = findViewById(R.id.txtinfo);

        ProductService.getInstance()
                .getProductsApi()
                .all()
                .enqueue(new Callback<List<ProductDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                        products = response.body();
                    }

                    @Override
                    public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                    }
                });

//        ProductService.getInstance()
//                .getProductsApi()
//                .getPostWithID(1)
//                .enqueue(new Callback<List<ProductImageDTO>>() {
//                    @Override
//                    public void onResponse(Call<List<ProductImageDTO>> call, Response<List<ProductImageDTO>> response) {
//                        List<ProductImageDTO> list = response.body();
//                        String str = "";
//                        for (ProductImageDTO item : list) {
//                            str += item.getPath() + "\n";
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<ProductImageDTO>> call, Throwable t) {
//
//                }
//        });
    }

}