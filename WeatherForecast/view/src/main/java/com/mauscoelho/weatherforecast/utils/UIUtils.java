package com.mauscoelho.weatherforecast.utils;


import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.mauscoelho.controllers.settings.App;
import com.mauscoelho.data.Forecast;
import com.mauscoelho.weatherforecast.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class UIUtils {

    public static final String TYPE_RAIN = "Rain";
    public static final String TYPE_CLOUDS = "Clouds";
    public static final String TYPE_CLEAR = "Clear";
    public static final String TYPE_SNOW = "Snow";


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getImageTypeGrey(String type) {
        switch (type){
            case TYPE_RAIN:
                return App.getsInstance().getDrawable(R.mipmap.ic_weather_rain);
            case TYPE_CLOUDS:
                return App.getsInstance().getDrawable(R.mipmap.ic_weather_cloudy);
            case TYPE_CLEAR:
                return App.getsInstance().getDrawable(R.mipmap.ic_weather_sunny);
            case TYPE_SNOW:
                return App.getsInstance().getDrawable(R.mipmap.ic_weather_snow);
        }
        return App.getsInstance().getDrawable(R.mipmap.ic_weather_sunny);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getImageTypeWhite(String type) {
        switch (type){
            case TYPE_RAIN:
                return App.getsInstance().getDrawable(R.mipmap.ic_weather_rain_white);
            case TYPE_CLOUDS:
                return App.getsInstance().getDrawable(R.mipmap.ic_weather_cloudy_white);
            case TYPE_CLEAR:
                return App.getsInstance().getDrawable(R.mipmap.ic_weather_sunny_white);
            case TYPE_SNOW:
                return App.getsInstance().getDrawable(R.mipmap.ic_weather_snow_white);
        }
        return App.getsInstance().getDrawable(R.mipmap.ic_weather_sunny);
    }

    public static Forecast[] distinctByDate(Forecast[] forecasts) {
        List<Forecast> distinctForecast = new ArrayList<>();
        Calendar firstDate = DateHelper.getDateWithoutTime(forecasts[0].dt);
        for (Forecast forecast : forecasts) {
            Calendar date = DateHelper.getDateWithoutTime(forecast.dt);
            if (date.after(firstDate)) {
                distinctForecast.add(forecast);
                firstDate = date;
            }
        }
        Forecast[] newForecast = new Forecast[distinctForecast.size()];
        distinctForecast.toArray(newForecast);
        return newForecast;
    }

}
