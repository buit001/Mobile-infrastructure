package com.example.siem.stappenteller;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * this class handles the network connection to the api.
 */
public class NetworkManager{

    private static NetworkManager mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private NetworkManager(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkManager  getInstance(Context context){
        if(mInstance == null){
            mInstance= new NetworkManager(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}