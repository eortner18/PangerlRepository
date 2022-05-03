package com.example.pangerlular;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CartProductAdapter extends ArrayAdapter<CartProduct>{

    private List<CartProduct> productList;
    private List<CartProduct> arraylist;
    private  Context context;

    DBManager db = new DBManager();
    Customer currentCustomer = LoginActivity.currentCustomer;
    public CartProductAdapter(@NonNull Context context, int resource, List<CartProduct> productList)
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
    public CartProduct getItem(int position) {
        return productList.get(position);
    }



    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        int phraseIndex = position;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_product_item, parent, false);
        }

        ImageView productImage = convertView.findViewById(R.id.product_imageview);
        TextView titleTextView = convertView.findViewById(R.id.name_textview);
        Button button = convertView.findViewById(R.id.delete);
        button.setTag(position);

        //set Image Resource
        new DownloadImageTask(productList.get(position).getProduct().getProductImageURL(), convertView).start();

        //set Text
        titleTextView.setText(productList.get(position).getProduct().getName() + " x " + productList.get(position).getAmount());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                arraylist.remove(pos);
                productList.remove(pos);
                CartProductAdapter.this.notifyDataSetChanged();

                db.resetCustomerInDatabase(currentCustomer);
            }
        });


        return convertView;
    }

    @Override
    public Filter getFilter() {


        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productList = (ArrayList<CartProduct>) results.values;
                notifyDataSetChanged();
                clear();
                for (int i = 0, l = productList.size(); i < l; i++) {
                    add((CartProduct) productList.get(i));
                }
                notifyDataSetInvalidated();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                productList= arraylist;
                FilterResults result = new FilterResults();
                List<CartProduct> filteredArray = new ArrayList<CartProduct>();



                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                if (constraint != null && constraint.length() > 0) {
                    for (int i = 0; i < productList.size(); i++) {
                        String dataName = productList.get(i).getProduct().getName();
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
}
