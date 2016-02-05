package com.mauscoelho.controllers.services;


import android.content.Context;

import com.google.gson.Gson;
import com.mauscoelho.controllers.settings.App;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.data.Forecast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class InternalStorageService {

    public static final String CITIES_KEY = "cities.json";
    static final int READ_BLOCK_SIZE = 100;
    private Gson gson;

    @Inject
    public InternalStorageService(Gson gson) {
        this.gson = gson;
    }

    public void saveCityForecast(CityForecast cityForecast) {
        if (cityForecast != null) {
            List<CityForecast> citiesForecasts = new ArrayList<>();
            checksWhetherCities(citiesForecasts, cityForecast);
            CityForecast[] cityArray = buildArray(citiesForecasts);
            saveCitiesForecast(cityArray);
        }
    }

    private CityForecast[] buildArray(List<CityForecast> citiesForecasts) {
        CityForecast[] cityArray = new CityForecast[citiesForecasts.size()];
        citiesForecasts.toArray(cityArray);
        return cityArray;
    }

    private void checksWhetherCities(List<CityForecast> citiesForecasts, CityForecast cityForecast) {
        if (containsCities()) {
            CityForecast[] savedCities = getCities();
            for (CityForecast item : savedCities) {
                citiesForecasts.add(item);
            }
        }
        citiesForecasts.add(cityForecast);
    }

    public void saveCitiesForecast(CityForecast[] citiesForecast) {
        try {
            String data = gson.toJson(citiesForecast);
            FileOutputStream fileout = App.getsInstance().openFileOutput(CITIES_KEY, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(data);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CityForecast getCity(String name){
        CityForecast[] cityForecasts = getCities();
        for (CityForecast cityForecast: cityForecasts) {
            if(cityForecast.city.name.contains(name))
                return cityForecast;
        }
        return null;
    }

    public CityForecast[] getCities() {
        CityForecast[] citiesForecast = null;
        String result = "";
        try {
            FileInputStream fileIn = App.getsInstance().openFileInput(CITIES_KEY);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                result += readstring;
            }
            InputRead.close();
            if (result != "") {
                citiesForecast = gson.fromJson(result, CityForecast[].class);
                return citiesForecast;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return citiesForecast;
    }

    public Boolean containsCities() {
        try {
            FileInputStream fileIn = App.getsInstance().openFileInput(CITIES_KEY);
            if (fileIn != null)
                return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void updateCityForecast(CityForecast newCityForecast, int position) {
        CityForecast[] citiesForecast = getCities();
        citiesForecast[position] = newCityForecast;
        saveCitiesForecast(citiesForecast);
    }
}
