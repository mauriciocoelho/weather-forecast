package com.mauscoelho.weatherforecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;

import com.mauscoelho.controllers.services.InternalStorageService;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.weatherforecast.adapters.MainAdapter;

import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.rv_forecast)
    RecyclerView rv_forecast;
    @Inject
    InternalStorageService internalStorageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectDependencies();
        injectView();
        buildForecasts();
    }

    private void buildForecasts() {
        CityForecast[] cityForecasts = internalStorageService.getCities();
        if(cityForecasts != null){
            MainAdapter mainAdapter = new MainAdapter(cityForecasts);
            rv_forecast.setAdapter(mainAdapter);
        }
    }


    @OnClick(R.id.toolbar_add)
    public void startAddOrEditActivity(ImageView toolbar_add){
        startActivity(new Intent(this, AddOrEditActivity.class));
    }

    private void injectView() {
        ButterKnife.inject(this);
    }

    private void injectDependencies() {
        IOpenWeatherMapComponent openWeatherMapComponent = DaggerIOpenWeatherMapComponent.create();
        openWeatherMapComponent.injectMainActivity(this);
    }
}
