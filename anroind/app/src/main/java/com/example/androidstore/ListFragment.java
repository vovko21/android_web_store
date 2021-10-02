package com.example.androidstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.androidstore.application.adapter.ProductAdapter;
import com.example.androidstore.constans.Urls;
import com.example.androidstore.dto.ProductDTO;
import com.example.androidstore.network.ImageRequester;
import com.example.androidstore.network.ProductsRequester;
import com.example.androidstore.network.services.ProductService;

import java.io.Serializable;
import java.util.List;

public class ListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "products";

    private ImageRequester imageRequester;
    private NetworkImageView myImage;

    private List<ProductDTO> products;

    public ListFragment() {
        this.products = ProductsRequester.getInstance().getAllProducts();
    }

    public ListFragment(List<ProductDTO> pr) {
        products = pr;
    }

    public static ListFragment newInstance () {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(view != null) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            ProductAdapter adapter = new ProductAdapter(getContext(), products);
            recyclerView.setAdapter(adapter);
        }
    }
}