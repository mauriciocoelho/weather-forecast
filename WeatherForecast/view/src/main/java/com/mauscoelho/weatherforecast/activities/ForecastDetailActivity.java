package com.mauscoelho.weatherforecast.activities;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mauscoelho.controllers.controllers.OpenWeatherMapController;
import com.mauscoelho.controllers.settings.Extras;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.data.Forecast;
import com.mauscoelho.weatherforecast.interfaces.DaggerIOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.interfaces.IOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.R;
import com.mauscoelho.weatherforecast.utils.DateHelper;
import com.mauscoelho.weatherforecast.utils.UIUtils;

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
    @InjectView(R.id.forecast_list)
    LinearLayout forecast_list;
    @Inject
    OpenWeatherMapController openWeatherMapController;
    private Activity activity = this;

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
        forecast_weather_image.setImageDrawable(UIUtils.getImageTypeGrey(cityForecast.forecasts[0].weather[0].main, activity));

        Forecast[] forecasts =  UIUtils.distinctByDate(cityForecast.forecasts);
        for (Forecast forecast: forecasts) {

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View forecastItemView = inflater.inflate(R.layout.card_forecast_item, null);

            TextView forecast_weather_day = (TextView)forecastItemView.findViewById(R.id.forecast_weather_day);
            TextView forecast_weather_value = (TextView)forecastItemView.findViewById(R.id.forecast_weather_value);
            ImageView forecast_weather_image = (ImageView) forecastItemView.findViewById(R.id.forecast_weather_image);

            forecast_weather_day.setText(DateHelper.getAbbrDay(forecast.dt));
            forecast_weather_value.setText(String.valueOf(Math.round(forecast.main.temp)));
            forecast_weather_image.setImageDrawable(UIUtils.getImageTypeWhite(forecast.weather[0].main, activity));

            forecast_list.addView(forecastItemView);
        }

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
