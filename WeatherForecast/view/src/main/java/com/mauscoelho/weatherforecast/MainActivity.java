package com.mauscoelho.weatherforecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.mauscoelho.controllers.services.InternalStorageService;
import com.mauscoelho.data.CityForecast;
import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {


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
