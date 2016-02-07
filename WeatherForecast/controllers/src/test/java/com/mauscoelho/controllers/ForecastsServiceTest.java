package com.mauscoelho.controllers;

import com.google.gson.Gson;
import com.mauscoelho.controllers.services.ForecastsService;
import com.mauscoelho.controllers.settings.Setting;
import com.mauscoelho.data.City;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.data.Forecast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import ollie.Ollie;

@RunWith(RobolectricTestRunner.class)
public class ForecastsServiceTest {

    ForecastsService forecastsService;

    @Before
    public void setup(){
        Ollie.with(Robolectric.application)
                .setName(Setting.DB_NAME)
                .setLogLevel(Ollie.LogLevel.FULL)
                .init();

        forecastsService = new ForecastsService(new Gson());
    }

    @Test(expected = NullPointerException.class)
    public void addCityForecast_whenCityForecastIsNull_returnException() {
        CityForecast cityForecast = null;
        forecastsService.save(cityForecast);
    }


    @Test(expected = NullPointerException.class)
    public void getCity_whenCityForecastIsNull_returnException() {
        String EMPTY = "";
        CityForecast cityForecastSaved = forecastsService.getCity(EMPTY);
    }


    public CityForecast pupulateCityForecast(){
        CityForecast cityForecast = new CityForecast();
        City city = new City();
        city.name = "teste";
        city.country = "teste";
        city.id = 1;
        city.population = 1;
        cityForecast.city = city;
        Forecast[] forecasts = new Forecast[1];
        Forecast forecast = new Forecast();
        forecasts[0] =  forecast;
        cityForecast.forecasts = forecasts;
        return cityForecast;
    }
}