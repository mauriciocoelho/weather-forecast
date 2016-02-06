package com.mauscoelho.controllers.controllers;

import com.mauscoelho.controllers.interfaces.IAction;
import com.mauscoelho.controllers.services.InternalStorageService;
import com.mauscoelho.controllers.services.OpenWeatherMapService;
import com.mauscoelho.data.CityForecast;

import javax.inject.Inject;

public class OpenWeatherMapController {

    private OpenWeatherMapService openWeatherMapService;
    private InternalStorageService internalStorageService;
    private boolean inUpdate = false;

    @Inject
    public OpenWeatherMapController(OpenWeatherMapService openWeatherMapService, InternalStorageService internalStorageService) {
        this.openWeatherMapService = openWeatherMapService;
        this.internalStorageService = internalStorageService;
    }

    public void saveCity(CityForecast cityForecast) {
        internalStorageService.saveCityForecast(cityForecast);
    }

    public void getForecastByCityName(final IAction<CityForecast> callback, String cityName) {
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
        if (cityForecasts != null & !inUpdate) {
            inUpdate = true;
            updateData(cityForecasts);
        }

        return cityForecasts;
    }

    public CityForecast getCity(String name) {
        return internalStorageService.getCity(name);
    }

    public void remove(CityForecast cityForecast){
        internalStorageService.remove(cityForecast);
    }

    public void updateData(final CityForecast[] cityForecasts) {
        for (int i = 0; i < cityForecasts.length; i++) {
            searchData(cityForecasts[i], i, cityForecasts.length);
        }

    }

    private void searchData(final CityForecast cityForecast, final int position, final int lastPosition) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                openWeatherMapService.getForecastByCityName(new IAction<CityForecast>() {
                    @Override
                    public void OnCompleted(CityForecast response) {
                        internalStorageService.updateCityForecast(response, position);
                        verifyUpdate(position, lastPosition);
                    }

                    @Override
                    public void OnError(CityForecast response) {
                        verifyUpdate(position, lastPosition);
                    }
                }, cityForecast.city.name);

            }
        }).start();
    }

    private void verifyUpdate(int position, int lastPosition) {
        if (position == lastPosition)
            inUpdate = false;
    }

}
