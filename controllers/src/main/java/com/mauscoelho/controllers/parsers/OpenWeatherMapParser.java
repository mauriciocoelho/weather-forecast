package com.mauscoelho.controllers.parsers;


import com.google.gson.Gson;
import com.mauscoelho.data.City;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.data.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class OpenWeatherMapParser {

    public static final String CITY = "city";
    public static final String LIST = "list";
    private Gson gson;

    @Inject
    public OpenWeatherMapParser(Gson gson) {
        this.gson = gson;
    }


    public CityForecast convertToCityForecast(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonCity = jsonObject.getJSONObject(CITY);
            JSONArray jsonArray = jsonObject.getJSONArray(LIST);
            City city =  gson.fromJson(jsonCity.toString(), City.class);
            Forecast[] forecasts = gson.fromJson(jsonArray.toString(), Forecast[].class);
            return createCityForecast(city, forecasts);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CityForecast createCityForecast(City city, Forecast[] forecasts) {
        CityForecast cityForecast = new CityForecast();
        cityForecast.city = city;
        cityForecast.forecasts = forecasts;
        return cityForecast;
    }

}
