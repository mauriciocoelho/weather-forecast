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
        internalStorageService.saveCity(cityForecast);
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
        return cityForecasts;
    }


}
