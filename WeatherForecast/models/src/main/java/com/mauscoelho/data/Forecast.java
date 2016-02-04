package com.mauscoelho.data;


import java.io.Serializable;

public class Forecast implements Serializable {
    public long dt;
    public Main main;
    public Weather[] weather;
    public Clouds clouds;
    public Wind wind;
    public Sys sys;
    public String dt_txt;
}
