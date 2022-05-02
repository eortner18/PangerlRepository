package com.example.pangerlular;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product>
{
    private ArrayList<Product> productList;

    public ProductAdapter(@NonNull Context context, int resource, ArrayList<Product> productList)
    {
        super(context, resource, productList);
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        int phraseIndex = position;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        ImageView productImage = convertView.findViewById(R.id.product_imageview);
        TextView titleTextView = convertView.findViewById(R.id.name_textview);

        //set Image Resource
        new DownloadImageTask(productList.get(position).getProductImageURL(), convertView).start();

        //set Text
        titleTextView.setText(productList.get(position).getName());

        return convertView;
    }
}
