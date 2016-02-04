package com.mauscoelho.weatherforecast.activities;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mauscoelho.controllers.controllers.OpenWeatherMapController;
import com.mauscoelho.controllers.settings.Extras;
import com.mauscoelho.data.City;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.weatherforecast.DaggerIOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.IOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ForecastDetailActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar_title)
    TextView toolbar_title;
    @InjectView(R.id.loader)
    ProgressBar loader;
    @Inject
    OpenWeatherMapController openWeatherMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);
        injectDependencies();
        injectView();
        bindView();
    }

    private void bindView() {
        String cityName = (String)getIntent().getSerializableExtra(Extras.OBJECT_FORECAST_NAME);
        toolbar_title.setText(cityName);
        getCityForecast(cityName);

    }

    private void getCityForecast(final String cityName) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CityForecast cityForecast = openWeatherMapController.getCity(cityName);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loader.setVisibility(View.GONE);
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    private void setLoaderOff(){
        loader.setVisibility(View.GONE);
    }

    @OnClick(R.id.toolbar_back)
    public void finishActivity(ImageView toolbar_back) {
        finish();
    }


    private void injectView() {
        ButterKnife.inject(this);
    }

    private void injectDependencies() {
        IOpenWeatherMapComponent openWeatherMapComponent = DaggerIOpenWeatherMapComponent.create();
        openWeatherMapComponent.injectForecastDetailActivity(this);
    }

}
