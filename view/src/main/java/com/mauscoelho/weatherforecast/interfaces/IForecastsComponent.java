package com.mauscoelho.weatherforecast.interfaces;


import com.mauscoelho.controllers.controllers.ForecastController;
import com.mauscoelho.controllers.modules.ForecastsModule;
import com.mauscoelho.controllers.parsers.OpenWeatherMapParser;
import com.mauscoelho.controllers.services.OpenWeatherMapService;
import com.mauscoelho.weatherforecast.activities.AddForecastActivity;
import com.mauscoelho.weatherforecast.activities.ForecastDetailActivity;
import com.mauscoelho.weatherforecast.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ForecastsModule.class})
public interface IForecastsComponent {

    void injectMainActivity(MainActivity mainActivity);
    void injectAddActivity(AddForecastActivity addForecastActivity);
    void injectForecastDetailActivity(ForecastDetailActivity forecastDetailActivity);

    OpenWeatherMapService provideOpenWeatherMapService();

    OpenWeatherMapParser provideOpenWeatherMapParser();

    ForecastController provideForecastController();

}
