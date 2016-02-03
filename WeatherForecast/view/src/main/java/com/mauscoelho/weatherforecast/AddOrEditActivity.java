package com.mauscoelho.weatherforecast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mauscoelho.controllers.interfaces.IAction;
import com.mauscoelho.controllers.services.OpenWeatherMapService;
import com.mauscoelho.data.City;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AddOrEditActivity extends AppCompatActivity {


    @Inject
    OpenWeatherMapService openWeatherMapService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit);
        injectDependencies();
        injectView();

    }

    @OnClick(R.id.toolbar_back)
    public void finishActivity(ImageView toolbar_back){
        finish();
    }

    public void searchCity(View v) {
        openWeatherMapService.getForecastByCityName(new IAction<City>() {
            @Override
            public void OnCompleted(City city) {

            }

            @Override
            public void OnError(City city) {

            }
        }, "Lages");

    }

    private void injectDependencies() {
        IOpenWeatherMapComponent openWeatherMapComponent = DaggerIOpenWeatherMapComponent.create();
        openWeatherMapComponent.injectAddOrEditActivity(this);
    }

    private void injectView() {
        ButterKnife.inject(this);
    }

}
