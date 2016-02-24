package com.mauscoelho.weatherforecast.utils;


import android.annotation.TargetApi;
import android.content.Context;
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



    public static Drawable getImageTypeGrey(String type, Context context) {
        switch (type){
            case TYPE_RAIN:
                return getDrawable(R.mipmap.ic_weather_rain, context);
            case TYPE_CLOUDS:
                return getDrawable(R.mipmap.ic_weather_cloudy, context);
            case TYPE_CLEAR:
                return getDrawable(R.mipmap.ic_weather_sunny, context);
            case TYPE_SNOW:
                return getDrawable(R.mipmap.ic_weather_snow, context);
        }
        return getDrawable(R.mipmap.ic_weather_sunny, context);
    }

    private static Drawable getDrawable(int id, Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return App.getsInstance().getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }


    public static Drawable getImageTypeWhite(String type, Context context) {
        switch (type){
            case TYPE_RAIN:
                return getDrawable(R.mipmap.ic_weather_rain_white,context);
            case TYPE_CLOUDS:
                return getDrawable(R.mipmap.ic_weather_cloudy_white,context);
            case TYPE_CLEAR:
                return getDrawable(R.mipmap.ic_weather_sunny_white,context);
            case TYPE_SNOW:
                return getDrawable(R.mipmap.ic_weather_snow_white,context);
        }
        return getDrawable(R.mipmap.ic_weather_sunny_white,context);
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
