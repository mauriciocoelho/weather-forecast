package com.mauscoelho.weatherforecast.network.services;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mauscoelho.weatherforecast.network.models.City;
import com.mauscoelho.weatherforecast.network.parsers.OpenWeatherMapParser;
import com.mauscoelho.weatherforecast.settings.App;
import com.mauscoelho.weatherforecast.settings.CacheRequest;
import com.mauscoelho.weatherforecast.settings.Endpoints;
import com.mauscoelho.weatherforecast.network.interfaces.IAction;


import javax.inject.Inject;

public class OpenWeatherMapService {

    OpenWeatherMapParser openWeatherMapParser;

    @Inject
    public OpenWeatherMapService(OpenWeatherMapParser openWeatherMapParser) {
        this.openWeatherMapParser = openWeatherMapParser;
    }

    public void getForecastByCityName(final IAction<City> callback, String cityName){

        String url = String.format(Endpoints.FORECAST_BY_CITY_NAME, cityName);

        CacheRequest request = new CacheRequest(Request.Method.GET,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.OnCompleted(openWeatherMapParser.convertToCity(new String(response.data)));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.OnError(null);
                    }
                });

        App.getsInstance().getmRequestQueue().add(request);

    }

}
