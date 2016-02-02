package com.mauscoelho.weatherforecast.network.parsers;


import com.google.gson.Gson;
import com.mauscoelho.weatherforecast.network.models.City;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class OpenWeatherMapParser {

    private Gson gson;

    @Inject
    public OpenWeatherMapParser(Gson gson) {
        this.gson = gson;
    }


    public City convertToCity(String json){
        City city = new City();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonCity = jsonObject.getJSONObject("city");
            city = gson.fromJson(jsonCity.toString(), City.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return city;
    }

}
