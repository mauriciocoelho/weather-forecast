package com.mauscoelho.weatherforecast.activities;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mauscoelho.controllers.controllers.OpenWeatherMapController;
import com.mauscoelho.controllers.settings.Extras;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.data.Forecast;
import com.mauscoelho.weatherforecast.adapters.ForecastsAdapter;
import com.mauscoelho.weatherforecast.interfaces.DaggerIOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.interfaces.IOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.R;
import com.mauscoelho.weatherforecast.utils.DateHelper;
import com.mauscoelho.weatherforecast.utils.UIUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ForecastDetailActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar_title)
    TextView toolbar_title;
    @InjectView(R.id.loader)
    ProgressBar loader;
    @InjectView(R.id.forecast_city_name)
    TextView forecast_city_name;
    @InjectView(R.id.forecast_weather_value)
    TextView forecast_weather_value;
    @InjectView(R.id.forecast_weather_description)
    TextView forecast_weather_description;
    @InjectView(R.id.forecast_weather_image)
    ImageView forecast_weather_image;
    @InjectView(R.id.rv_forecast)
    RecyclerView rv_forecast;
    @Inject
    OpenWeatherMapController openWeatherMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);
        injectDependencies();
        injectView();
        buildView();
    }

    private void buildView() {
        String cityName = (String) getIntent().getSerializableExtra(Extras.OBJECT_FORECAST_NAME);
        toolbar_title.setText(cityName);
        getCityForecast(cityName);

    }

    private void getCityForecast(final String cityName) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final CityForecast cityForecast = openWeatherMapController.getCity(cityName);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bindView(cityForecast);
                    }
                });
            }
        }).start();
    }

    private void bindView(CityForecast cityForecast) {
        loader.setVisibility(View.GONE);
        forecast_city_name.setText(cityForecast.city.name);
        forecast_weather_value.setText(String.valueOf(Math.round(cityForecast.forecasts[0].main.temp)));
        forecast_weather_description.setText(cityForecast.forecasts[0].weather[0].description);
        forecast_weather_image.setImageDrawable(UIUtils.getImageTypeGrey(cityForecast.forecasts[0].weather[0].main));
        rv_forecast.setAdapter(new ForecastsAdapter(UIUtils.distinctByDate(cityForecast.forecasts)));
        rv_forecast.setVisibility(View.VISIBLE);
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
