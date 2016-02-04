package com.mauscoelho.weatherforecast.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mauscoelho.controllers.settings.Extras;
import com.mauscoelho.data.City;
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
    @Inject


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
