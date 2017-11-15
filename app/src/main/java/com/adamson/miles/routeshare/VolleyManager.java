package com.adamson.miles.routeshare;
import android.content.Context;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
/**
 * Created by miles on 2017-11-15.
 */

public class VolleyManager {

    private static VolleyManager mInstance = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleyManager(Context context){
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }
        });
    }

    public static VolleyManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleyManager(context);
        }
        return mInstance;
    }

    // requests the URL of a completely random image from the server.
    // then requests the image and puts it in the NetworkImageView
    public void randomNetworkImage(final NetworkImageView networkImageView){
        final String url = "http://192.168.1.69/climb/select_random_photo.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        networkImageView.setImageUrl(response, imageLoader);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

}

