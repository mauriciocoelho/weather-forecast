package com.mauscoelho.weatherforecast.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mauscoelho.weatherforecast.DaggerIOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.IOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.R;

import butterknife.ButterKnife;

public class ForecastDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectDependencies();
        injectView();
    }

    private void injectView() {
        ButterKnife.inject(this);
    }

    private void injectDependencies() {
        IOpenWeatherMapComponent openWeatherMapComponent = DaggerIOpenWeatherMapComponent.create();
        openWeatherMapComponent.injectForecastDetailActivity(this);
    }

}
