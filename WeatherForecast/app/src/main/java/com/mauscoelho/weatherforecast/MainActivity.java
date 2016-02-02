package com.mauscoelho.weatherforecast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mauscoelho.weatherforecast.network.interfaces.DaggerIOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.network.interfaces.IAction;
import com.mauscoelho.weatherforecast.network.interfaces.IOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.network.services.OpenWeatherMapService;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {


    @Inject
    OpenWeatherMapService openWeatherMapService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectDependencies();



    }

    public void teste(View v) {
        openWeatherMapService.getForecastByCityName(new IAction<Boolean>() {
            @Override
            public void OnCompleted(Boolean response) {
                Boolean teste = response;
            }

            @Override
            public void OnError(Boolean response) {
                Boolean teste = response;
            }
        }, "Lages");

    }

    private void injectDependencies() {
        IOpenWeatherMapComponent openWeatherMapComponent = DaggerIOpenWeatherMapComponent.create();
        openWeatherMapComponent.inject(this);
    }
}
