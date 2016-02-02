package com.mauscoelho.controllers.parsers;


import com.google.gson.Gson;
import com.mauscoelho.data.City;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class OpenWeatherMapParser {

    public static final String CITY = "city";
    private Gson gson;

    @Inject
    public OpenWeatherMapParser(Gson gson) {
        this.gson = gson;
    }


    public City convertToCity(String json){
        City city = new City();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonCity = jsonObject.getJSONObject(CITY);
            city = gson.fromJson(jsonCity.toString(), City.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return city;
    }

}
