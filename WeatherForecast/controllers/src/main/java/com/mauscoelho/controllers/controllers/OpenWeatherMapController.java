package com.mauscoelho.controllers.controllers;

import com.mauscoelho.controllers.interfaces.IAction;
import com.mauscoelho.controllers.services.InternalStorageService;
import com.mauscoelho.controllers.services.OpenWeatherMapService;
import com.mauscoelho.data.CityForecast;

import javax.inject.Inject;

public class OpenWeatherMapController {

    private OpenWeatherMapService openWeatherMapService;
    private InternalStorageService internalStorageService;

    @Inject
    public OpenWeatherMapController(OpenWeatherMapService openWeatherMapService, InternalStorageService internalStorageService) {
        this.openWeatherMapService = openWeatherMapService;
        this.internalStorageService = internalStorageService;
    }

    public void saveCity(CityForecast cityForecast){
        internalStorageService.saveCityForecast(cityForecast);
    }

    public void getForecastByCityName(final IAction<CityForecast> callback, String cityName){
        openWeatherMapService.getForecastByCityName(new IAction<CityForecast>() {
            @Override
            public void OnCompleted(CityForecast cityForecast) {
                callback.OnCompleted(cityForecast);
            }

            @Override
            public void OnError(CityForecast cityForecast) {
                callback.OnError(cityForecast);
            }
        }, cityName);
    }

    public CityForecast[] getCities() {
        CityForecast[] cityForecasts = internalStorageService.getCities();
        if(cityForecasts != null)
            updateData(cityForecasts);

        return cityForecasts;
    }

    public CityForecast getCity(String name){
        return internalStorageService.getCity(name);
    }

    public void updateData(final CityForecast[] cityForecasts) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < cityForecasts.length; i++) {
                    searchData(cityForecasts[i], i);
                }

            }
        }).start();
    }

    private void searchData(CityForecast cityForecast, final int position) {
        openWeatherMapService.getForecastByCityName(new IAction<CityForecast>() {
            @Override
            public void OnCompleted(CityForecast response) {
                internalStorageService.updateCityForecast(response, position);
            }
            @Override
            public void OnError(CityForecast response) {

            }
        }, cityForecast.city.name);
    }

}
