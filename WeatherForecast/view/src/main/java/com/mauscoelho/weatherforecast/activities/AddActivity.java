package com.mauscoelho.weatherforecast.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mauscoelho.controllers.controllers.OpenWeatherMapController;
import com.mauscoelho.controllers.interfaces.IAction;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.weatherforecast .DaggerIOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.IOpenWeatherMapComponent;
import com.mauscoelho.weatherforecast.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AddActivity extends AppCompatActivity {

    private CityForecast cityForecast;
    @InjectView(R.id.toolbar_text)
    EditText toolbar_text;
    @InjectView(R.id.card_city)
    LinearLayout card_city;
    @InjectView(R.id.city_name)
    TextView city_name;
    @InjectView(R.id.city_country)
    TextView city_country;
    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @InjectView(R.id.loader)
    ProgressBar loader;
    @Inject
    OpenWeatherMapController openWeatherMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit);
        injectDependencies();
        injectView();

    }

    @OnClick(R.id.toolbar_back)
    public void finishActivity(ImageView toolbar_back) {
        finish();
    }

    @OnClick(R.id.toolbar_search)
    public void onClickSearch(ImageView toolbar_search) {
        searchCity();
    }

    @OnClick(R.id.card_city_item)
    public void saveCity(LinearLayout card_city_item){
        openWeatherMapController.saveCity(cityForecast);
        Snackbar.make(coordinatorLayout,R.string.saved, Snackbar.LENGTH_LONG).show();
    }

    public void searchCity() {
        loader.setVisibility(View.VISIBLE);
        String citySearch = toolbar_text.getText().toString();
        if (!citySearch.isEmpty())
            openWeatherMapController.getForecastByCityName(new IAction<CityForecast>() {
                @Override
                public void OnCompleted(CityForecast cityForecast) {
                    resolveView(cityForecast);
                }

                @Override
                public void OnError(CityForecast cityForecast) {

                }
            }, citySearch);
    }

    private void resolveView(CityForecast cityForecast) {
        if (cityForecast != null) {
            this.cityForecast = cityForecast;
            bindCity(cityForecast);
        }
        else
            unbindCity();
    }

    private void unbindCity() {
        card_city.setVisibility(View.GONE);
    }

    private void bindCity(CityForecast cityForecast) {
        loader.setVisibility(View.GONE);
        card_city.setVisibility(View.VISIBLE);
        city_name.setText(cityForecast.city.name);
        city_country.setText(cityForecast.city.country);
    }

    private void injectDependencies() {
        IOpenWeatherMapComponent openWeatherMapComponent = DaggerIOpenWeatherMapComponent.create();
        openWeatherMapComponent.injectAddActivity(this);
    }

    private void injectView() {
        ButterKnife.inject(this);
    }

}
