package com.mauscoelho.weatherforecast.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mauscoelho.controllers.settings.Extras;
import com.mauscoelho.data.CityForecast;
import com.mauscoelho.data.Forecast;
import com.mauscoelho.weatherforecast.R;
import com.mauscoelho.weatherforecast.activities.ForecastDetailActivity;
import com.mauscoelho.weatherforecast.utils.DateHelper;
import com.mauscoelho.weatherforecast.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForecastsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Forecast[] forecasts;

    public ForecastsAdapter(Forecast[] forecasts) {
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
        Forecast forecast = forecasts[position];
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                bindMatchItem((ForecastsViewHolder) viewHolder, forecast);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_forecast_item, viewGroup, false);
        return new ForecastsViewHolder(view);
    }


    private void bindMatchItem(ForecastsViewHolder viewHolder, final Forecast forecast) {
        viewHolder.forecast_weather_day.setText(DateHelper.getAbbrDay(forecast.dt));
        viewHolder.forecast_weather_value.setText(String.valueOf(Math.round(forecast.main.temp)));
        viewHolder.forecast_weather_description.setText(forecast.weather[0].description);
        viewHolder.forecast_weather_image.setImageDrawable(UIUtils.getImageTypeWhite(forecast.weather[0].main));
    }

    public static class ForecastsViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.forecast_weather_day)
        TextView forecast_weather_day;
        @InjectView(R.id.forecast_weather_value)
        TextView forecast_weather_value;
        @InjectView(R.id.forecast_weather_description)
        TextView forecast_weather_description;
        @InjectView(R.id.forecast_weather_image)
        ImageView forecast_weather_image;

        public ForecastsViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }
}
