package com.mauscoelho.weatherforecast.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mauscoelho.data.CityForecast;
import com.mauscoelho.weatherforecast.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CityForecast[] forecasts;

    public MainAdapter(CityForecast[] forecasts) {
        this.forecasts = forecasts;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return forecasts.length;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CityForecast forecast = forecasts[position];
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                bindMatchItem((MainViewHolder) viewHolder, forecast);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_forecast, viewGroup, false);
        return new MainViewHolder(view);
    }


    private void bindMatchItem(MainViewHolder viewHolder, final CityForecast forecast) {
        viewHolder.forecast_city_name.setText(forecast.city.name);
        viewHolder.forecast_weather_value.setText(String.valueOf(Math.round(forecast.forecasts[0].main.temp)));
        viewHolder.forecast_weather_description.setText(forecast.forecasts[0].weather[0].description);
    }


    public static class MainViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.forecast_city_name)
        TextView forecast_city_name;
        @InjectView(R.id.forecast_weather_value)
        TextView forecast_weather_value;
        @InjectView(R.id.forecast_weather_description)
        TextView forecast_weather_description;

        public MainViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }
}
