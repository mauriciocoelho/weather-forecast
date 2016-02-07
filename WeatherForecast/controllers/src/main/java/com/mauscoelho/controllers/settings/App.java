package com.mauscoelho.controllers.settings;


import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import ollie.Ollie;

public class App extends Application {

    private static App sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        volley();
    }

    private void volley() {
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        sInstance = this;
    }

    public synchronized static App getsInstance(){
        return sInstance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }

}
