package com.mauscoelho.controllers.services;


import android.content.Context;
import android.support.v4.content.res.TypedArrayUtils;

import com.google.gson.Gson;
import com.mauscoelho.controllers.settings.App;
import com.mauscoelho.controllers.settings.Setting;
import com.mauscoelho.controllers.util.ArrayUtils;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.data.Forecast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class InternalStorageService {

    public static final String CITIES_KEY = "cities.json";
    static final int READ_BLOCK_SIZE = 100;
    private Gson gson;
    private CityForecast[] citiesForecastCache;

    @Inject
    public InternalStorageService(Gson gson) {
        this.gson = gson;
    }

    public void saveCityForecast(CityForecast cityForecast) {
        CityForecast[] savedCities = getCities();
        if (!verifyIfContainsCity(cityForecast, savedCities)) {
            List<CityForecast> citiesForecasts = checksWhetherCities(savedCities, cityForecast);
            CityForecast[] cityArray = buildArray(citiesForecasts);
            saveCitiesForecast(cityArray);
        }
    }

    private CityForecast[] buildArray(List<CityForecast> citiesForecasts) {
        CityForecast[] cityArray = new CityForecast[citiesForecasts.size()];
        citiesForecasts.toArray(cityArray);
        return cityArray;
    }

    private List<CityForecast> checksWhetherCities(CityForecast[] savedCities, CityForecast cityForecast) {
        List<CityForecast> newCitiesForecasts = new ArrayList<>();
        if (containsCities()) {
            for (CityForecast item : savedCities) {
                newCitiesForecasts.add(item);
            }
        }
        newCitiesForecasts.add(cityForecast);
        return newCitiesForecasts;
    }

    private boolean verifyIfContainsCity(CityForecast cityForecast, CityForecast[] savedCities) {
        if(savedCities == null)
            return false;

        for (CityForecast item : savedCities) {
            if (item.city.name.contains(cityForecast.city.name))
                return true;
        }
        return false;
    }

    public void saveCitiesForecast(CityForecast[] citiesForecast) {
        try {
            Setting.edited = true;
            String data = gson.toJson(citiesForecast);
            FileOutputStream fileout = App.getsInstance().openFileOutput(CITIES_KEY, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(data);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CityForecast getCity(String name) {
        CityForecast[] cityForecasts = getCities();
        for (CityForecast cityForecast : cityForecasts) {
            if (cityForecast.city.name.contains(name))
                return cityForecast;
        }
        return null;
    }

    public int getCityPosition(CityForecast[] cityForecasts, String name) {
        for (int i = 0; i < cityForecasts.length; i++) {
            if (cityForecasts[i].city.name.contains(name))
                return i;
        }
        return -1;
    }


    public void remove(CityForecast cityForecast) {
        CityForecast[] citiesForecasts = getCities();
        int position = getCityPosition(citiesForecasts, cityForecast.city.name);
        if (position != -1) {
            citiesForecasts[position] = null;
            CityForecast[] removed = (CityForecast[]) ArrayUtils.resizeArray(citiesForecasts, citiesForecasts.length - 1);
            saveCitiesForecast(removed);
        }
    }

    public CityForecast[] getCities() {
        if (Setting.edited | Setting.firstAccess | citiesForecastCache == null) {
            Setting.edited = false;
            Setting.firstAccess = false;
            citiesForecastCache = getCitiesFromJson();
        }
        return citiesForecastCache;
    }

    private CityForecast[] getCitiesFromJson() {
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
