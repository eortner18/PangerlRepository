package com.example.pangerlular;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

class DownloadImageTask extends Thread
{
    String URL;
    Handler mainHandler= new Handler();
    ImageView imageView;

    public DownloadImageTask(String URL, ImageView imageView)
    {
        this.URL = URL;
        this.imageView = imageView;
    }

    @Override
    public void run(){
        InputStream inputStream = null;
        try
        {
            inputStream = new java.net.URL(URL).openStream();
            Bitmap bmImage = BitmapFactory.decodeStream(inputStream);

            mainHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    imageView.setImageBitmap(bmImage);
                }
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
