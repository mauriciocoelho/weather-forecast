package com.mauscoelho.weatherforecast;


import com.mauscoelho.controllers.controllers.OpenWeatherMapController;
import com.mauscoelho.controllers.modules.OpenWeatherMapModule;
import com.mauscoelho.controllers.parsers.OpenWeatherMapParser;
import com.mauscoelho.controllers.services.OpenWeatherMapService;
import com.mauscoelho.weatherforecast.activities.AddActivity;
import com.mauscoelho.weatherforecast.activities.ForecastDetailActivity;
import com.mauscoelho.weatherforecast.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {OpenWeatherMapModule.class})
public interface IOpenWeatherMapComponent {

    void injectMainActivity(MainActivity mainActivity);
    void injectAddActivity(AddActivity addActivity);
    void injectForecastDetailActivity(ForecastDetailActivity forecastDetailActivity);

    OpenWeatherMapService provideOpenWeatherMapService();

    OpenWeatherMapParser provideOpenWeatherMapParser();

    OpenWeatherMapController provideOpenWeatherMapController();

}
