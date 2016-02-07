package com.mauscoelho.controllers.services;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.data.ollie.ForecastOllie;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ollie.query.Select;

public class ForecastsService {

    private Gson gson;

    @Inject
    public ForecastsService(Gson gson) {
        this.gson = gson;
    }

    public void save(CityForecast cityForecast) {
        if(cityForecast == null)
            throw new NullPointerException("cityForest is null");

        if (getByName(cityForecast.city.name) == null) {
            ForecastOllie forecast = new ForecastOllie();
            forecast.city = cityForecast.city.name;
            forecast.json = gson.toJson(cityForecast);
            forecast.save();
        }
    }

    public List<CityForecast> getCities() {
        List<ForecastOllie> forecastOllieList = Select.from(ForecastOllie.class).fetch();
        return convertToCityForecast(forecastOllieList);
    }

    @NonNull
    private List<CityForecast> convertToCityForecast(List<ForecastOllie> forecastOllieList) {
        List<CityForecast> citiesForecast = new ArrayList<>();
        for (ForecastOllie forecastOllie : forecastOllieList) {
            CityForecast cityForecast = gson.fromJson(forecastOllie.json, CityForecast.class);
            citiesForecast.add(cityForecast);
        }
        return citiesForecast;
    }

    public void update(CityForecast response, String city) {
        ForecastOllie foreccastOllie = getByName(city);
        foreccastOllie.json = gson.toJson(response);
        foreccastOllie.save();
    }

    private ForecastOllie getByName(String city) {
        return Select.from(ForecastOllie.class).where("city=?", city).fetchSingle();
    }

    public CityForecast getCity(String city) {
        if(city.isEmpty())
            throw new NullPointerException("param is empty");

        ForecastOllie forecastOllie = getByName(city);
        return gson.fromJson(forecastOllie.json, CityForecast.class);
    }

    public void remove(CityForecast cityForecast) {
        ForecastOllie forecastOllie = getByName(cityForecast.city.name);
        forecastOllie.delete();
    }
}
