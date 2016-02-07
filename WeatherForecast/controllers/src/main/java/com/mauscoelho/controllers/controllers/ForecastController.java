package com.mauscoelho.controllers.controllers;

import com.mauscoelho.controllers.interfaces.IAction;
import com.mauscoelho.controllers.services.ForecastsService;
import com.mauscoelho.controllers.services.OpenWeatherMapService;
import com.mauscoelho.data.CityForecast;

import java.util.List;

import javax.inject.Inject;

public class ForecastController {

    private OpenWeatherMapService openWeatherMapService;
    private ForecastsService forecastsService;
    private boolean inUpdate = false;

    @Inject
    public ForecastController(OpenWeatherMapService openWeatherMapService, ForecastsService forecastsService) {
        this.openWeatherMapService = openWeatherMapService;
        this.forecastsService = forecastsService;
    }

    public void saveCity(CityForecast cityForecast) {
        forecastsService.save(cityForecast);
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

    public List<CityForecast> getCities() {
        List<CityForecast> citiesForecast = forecastsService.getCities();
        if (citiesForecast != null & !inUpdate) {
            inUpdate = true;
            updateData(citiesForecast);
        }
        return citiesForecast;
    }

    public void remove(CityForecast cityForecast){
        forecastsService.remove(cityForecast);
    }

    public void updateData(final List<CityForecast> cityForecasts) {
        for (int i = 0; i < cityForecasts.size(); i++) {
            searchData(cityForecasts.get(i), i, cityForecasts.size());
        }

    }

    private void searchData(final CityForecast cityForecast, final int position, final int lastPosition) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                openWeatherMapService.getForecastByCityName(new IAction<CityForecast>() {
                    @Override
                    public void OnCompleted(CityForecast response) {
                        forecastsService.update(response, cityForecast.city.name);
                        verifyInUpdate(position, lastPosition);
                    }

                    @Override
                    public void OnError(CityForecast response) {
                        verifyInUpdate(position, lastPosition);
                    }
                }, cityForecast.city.name);

            }
        }).start();
    }

    private void verifyInUpdate(int position, int lastPosition) {
        if (position == lastPosition)
            inUpdate = false;
    }

    public CityForecast getCity(String cityName) {
        return forecastsService.getCity(cityName);
    }
}
