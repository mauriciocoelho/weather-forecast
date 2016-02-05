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

import com.mauscoelho.controllers.controllers.OpenWeatherMapController;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.weatherforecast.interfaces.DaggerIOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.interfaces.IOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.R;
import com.mauscoelho.weatherforecast.adapters.MainAdapter;

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
    OpenWeatherMapController openWeatherMapController;
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
        startActivity(new Intent(this, AddActivity.class));
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
                final CityForecast[] cityForecasts = openWeatherMapController.getCities();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bindView(cityForecasts);
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    private void bindView(CityForecast[] cityForecasts) {
        if(cityForecasts != null){
            rv_forecast.setAdapter(new MainAdapter(cityForecasts, activity));
        }
        loader.setVisibility(View.GONE);
    }

    private void injectView() {
        ButterKnife.inject(this);
    }

    private void injectDependencies() {
        IOpenWeatherMapComponent openWeatherMapComponent = DaggerIOpenWeatherMapComponent.create();
        openWeatherMapComponent.injectMainActivity(this);
    }
}
