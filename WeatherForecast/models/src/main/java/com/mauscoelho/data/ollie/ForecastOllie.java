package com.mauscoelho.data.ollie;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("forecasts")
public class ForecastOllie extends Model{
    @Column("city")
    public String city;
    @Column("json")
    public String json;

}
