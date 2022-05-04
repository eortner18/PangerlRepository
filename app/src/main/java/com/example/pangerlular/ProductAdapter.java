package com.example.pangerlular;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProductAdapter extends ArrayAdapter<Product>
{
    private List<Product> productList;
    private List<Product> arraylist;
    private  Context context;
    public ProductAdapter(@NonNull Context context, int resource, List<Product> productList)
    {
        super(context, resource, productList);
        this.productList = productList;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(productList);
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }



    @SuppressLint("SetTextI18n")
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
            titleTextView.setText(productList.get(position).getName() + " " + productList.get(position).getPrice() + " €");


        return convertView;
    }

    @Override
    public Filter getFilter() {


        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productList = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
                clear();
                for (int i = 0, l = productList.size(); i < l; i++) {
                    add((Product) productList.get(i));
                }
                notifyDataSetInvalidated();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                productList= arraylist;
                FilterResults result = new FilterResults();
                List<Product> filteredArray = new ArrayList<Product>();



                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                if (constraint != null && constraint.length() > 0) {
                    for (int i = 0; i < productList.size(); i++) {
                        String dataName = productList.get(i).getName();
                        if (dataName.toLowerCase().startsWith(constraint.toString())) {
                            filteredArray.add(productList.get(i));
                        }
                    }
                    result.values = filteredArray;
                    result.count = filteredArray.size();
                }else{

                        result.values = productList;
                        result.count = productList.size();

                }


                Log.e("VALUES", result.values.toString());

                return result;
            }
        };

    }

    public void filterCategory(String category) {
        productList = arraylist;
        List<Product> filtered = new ArrayList<>();
        if(!category.equals("Any")){
            for (Product product :
                    productList) {
                if((product.getType()).equalsIgnoreCase(category)){
                    filtered.add(product);
                }
            }
            productList = filtered;
        }
        notifyDataSetChanged();
    }
}
