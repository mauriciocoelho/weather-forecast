package com.mauscoelho.weatherforecast.network.interfaces;

import com.mauscoelho.weatherforecast.MainActivity;
import com.mauscoelho.weatherforecast.network.modules.OpenWeatherMapModule;
import com.mauscoelho.weatherforecast.network.services.OpenWeatherMapService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {OpenWeatherMapModule.class})
public interface IOpenWeatherMapComponent {
    void inject(MainActivity mainActivity);

    OpenWeatherMapService provideOpenWeatherMapService();

}
