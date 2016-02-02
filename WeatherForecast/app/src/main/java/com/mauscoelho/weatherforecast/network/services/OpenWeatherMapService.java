package com.mauscoelho.weatherforecast.network.services;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mauscoelho.weatherforecast.settings.App;
import com.mauscoelho.weatherforecast.settings.CacheRequest;
import com.mauscoelho.weatherforecast.settings.Endpoints;
import com.mauscoelho.weatherforecast.network.interfaces.IAction;

import org.json.JSONObject;

import javax.inject.Inject;

public class OpenWeatherMapService {

    @Inject
    public OpenWeatherMapService() {

    }

    public void getForecastByCityName(final IAction<Boolean> callback, String cityName){

        String url = String.format(Endpoints.FORECAST_BY_CITY_NAME, cityName);

        CacheRequest request = new CacheRequest(Request.Method.GET,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.OnCompleted(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.OnError(true);
                    }
                });

        App.getsInstance().getmRequestQueue().add(request);

    }

}
