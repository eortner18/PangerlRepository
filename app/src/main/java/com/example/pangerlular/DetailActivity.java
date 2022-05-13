package com.example.pangerlular;

import static com.example.pangerlular.LoginActivity.currentCustomer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity
{
    private static final DecimalFormat df = new DecimalFormat("0.00");
    DBManager db = new DBManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Product currentProduct = (Product) getIntent().getSerializableExtra("Product");

        ImageView image = findViewById(R.id.imageView2);
        new DownloadImageTask(currentProduct.getProductImageURL(), image).start();

        TextView nameView = findViewById(R.id.productDetails);
        nameView.setText(currentProduct.getName());

        EditText menge = findViewById(R.id.menge);
        menge.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String mengeString = menge.getText().toString();
                try {
                    double menge = Double.parseDouble(mengeString);

                    double price = menge * currentProduct.getPrice();
                    TextView priceView = findViewById(R.id.gesamtpreis);
                    priceView.setText(df.format(price) + " €");
                } catch (NumberFormatException nfe) {

                }
            }
        });

        Button cartButton = findViewById(R.id.bestellen);
        cartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String mengeString = menge.getText().toString();

                try{
                    int menge = Integer.parseInt(mengeString);
                    if(menge<=0){
                        throw new NumberFormatException();
                    }

                    CartProduct cProduct = new CartProduct(menge, currentProduct);
                    currentCustomer.getCart().addProduct(cProduct);
                    db.updateCustomer(currentCustomer);

                    Toast.makeText(getApplicationContext(), "Produkt hinzugefügt", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                catch (NumberFormatException nfe){
                    Toast.makeText(getApplicationContext(), "Feld 'Menge' ist keine einstellige oder positive Zahl", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageButton cartImgButton = findViewById(R.id.ShoppingCartImageButton);
        cartImgButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startCartActivity(view);
            }
        });

        ImageButton settImgButton = findViewById(R.id.btnSettings);
        settImgButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startSettingsActivity(view);
            }
        });
    }

    public void startCartActivity(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }


    public void startSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
