package com.mauscoelho.controllers.modules;

import com.google.gson.Gson;
import com.mauscoelho.controllers.parsers.OpenWeatherMapParser;
import com.mauscoelho.controllers.services.OpenWeatherMapService;

import dagger.Module;
import dagger.Provides;

@Module
public class OpenWeatherMapModule {

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
}
