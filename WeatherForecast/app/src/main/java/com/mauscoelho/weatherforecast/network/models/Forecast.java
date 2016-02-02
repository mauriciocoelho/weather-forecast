package com.mauscoelho.weatherforecast.network.models;


public class Forecast{
    public long dt;
    public Main main;
    public Weather[] weather;
    public Clouds clouds;
    public Wind wind;
    public Sys sys;
    public String dt_txt;
}
