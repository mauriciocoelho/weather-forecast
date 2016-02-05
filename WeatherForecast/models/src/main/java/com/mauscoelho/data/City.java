package com.mauscoelho.data;

import java.io.Serializable;

public class City implements Serializable {
    public long id;
    public String name;
    public Coord coord;
    public String country;
    public long population;
}
