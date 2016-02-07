package com.mauscoelho.weatherforecast.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mauscoelho.controllers.controllers.ForecastController;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.weatherforecast.interfaces.DaggerIForecastsComponent;
import com.mauscoelho.weatherforecast.interfaces.IForecastsComponent;
import com.mauscoelho.weatherforecast.R;
import com.mauscoelho.weatherforecast.adapters.MainAdapter;

import java.util.List;

import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.loader)
    ProgressBar loader;
    @InjectView(R.id.rv_forecast)
    RecyclerView rv_forecast;
    @Inject
    ForecastController forecastController;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectDependencies();
        injectView();
        buildForecasts();
    }

    @OnClick(R.id.toolbar_add)
    public void startAddOrEditActivity(ImageView toolbar_add){
        startActivity(new Intent(this, AddForecastActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildForecasts();
    }

    private void buildForecasts() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<CityForecast> citiesForecast = forecastController.getCities();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bindView(citiesForecast);
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    private void bindView(List<CityForecast> citiesForecast) {
        if(citiesForecast != null){
            rv_forecast.setAdapter(new MainAdapter(citiesForecast, activity));
        }
        loader.setVisibility(View.GONE);
    }

    private void injectView() {
        ButterKnife.inject(this);
    }

    private void injectDependencies() {
        IForecastsComponent openWeatherMapComponent = DaggerIForecastsComponent.create();
        openWeatherMapComponent.injectMainActivity(this);
    }
}
