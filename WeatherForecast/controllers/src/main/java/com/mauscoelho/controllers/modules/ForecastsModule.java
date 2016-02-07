package com.mauscoelho.controllers.modules;

import com.google.gson.Gson;
import com.mauscoelho.controllers.controllers.ForecastController;
import com.mauscoelho.controllers.parsers.OpenWeatherMapParser;
import com.mauscoelho.controllers.services.ForecastsService;
import com.mauscoelho.controllers.services.OpenWeatherMapService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ForecastsModule {

    @Provides
    Gson provideGson(){
        return new Gson();
    }

    @Provides
    OpenWeatherMapParser provideOpenWeatherMapParser(){
        return new OpenWeatherMapParser(provideGson());
    }

    @Provides
    OpenWeatherMapService provideOpenWeatherMapService(){
        return  new OpenWeatherMapService( provideOpenWeatherMapParser());
    }


    @Singleton
    @Provides
    ForecastsService provideForecastsService(){
        return new ForecastsService(provideGson());
    }

    @Provides
    ForecastController provideForecastController(){
        return  new ForecastController( provideOpenWeatherMapService(), provideForecastsService());
    }
}
