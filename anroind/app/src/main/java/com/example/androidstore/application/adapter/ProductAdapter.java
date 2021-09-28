package com.example.androidstore.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidstore.R;
import com.example.androidstore.dto.ProductDTO;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<ProductDTO> productDTOs;

    public ProductAdapter(Context context, List<ProductDTO> productDTOs) {
        this.productDTOs = productDTOs;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list__item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        ProductDTO productDTO = productDTOs.get(position);
        holder.nameView.setText(productDTO.getName());
        holder.priceView.setText(String.valueOf(productDTO.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productDTOs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, priceView;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            nameView = (TextView) view.findViewById(R.id.name);
            priceView = (TextView) view.findViewById(R.id.price);
        }
    }
}
