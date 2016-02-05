package com.mauscoelho.data;


import java.io.Serializable;

public class CityForecast implements Serializable {
    public City city;
    public Forecast[] forecasts;
}
