package com.mauscoelho.weatherforecast.network.modules;

import com.mauscoelho.weatherforecast.network.services.OpenWeatherMapService;

import dagger.Module;
import dagger.Provides;

@Module
public class OpenWeatherMapModule {

    @Provides
    OpenWeatherMapService provideOpenWeatherMapService(){
        return  new OpenWeatherMapService();
    }

}
