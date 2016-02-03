package com.mauscoelho.weatherforecast;


import com.mauscoelho.controllers.modules.OpenWeatherMapModule;
import com.mauscoelho.controllers.services.OpenWeatherMapService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {OpenWeatherMapModule.class})
public interface IOpenWeatherMapComponent {

    void injectMainActivity(MainActivity mainActivity);
    void injectAddOrEditActivity(AddOrEditActivity addOrEditActivity);

    OpenWeatherMapService provideOpenWeatherMapService();

}
