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
import com.mauscoelho.weatherforecast.R;
import com.mauscoelho.weatherforecast.utils.UIUtils;
import com.mauscoelho.weatherforecast.activities.ForecastDetailActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CityForecast> forecasts;
    private Activity activity;

    public MainAdapter(List<CityForecast> forecasts, Activity activity) {
        this.forecasts = forecasts;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CityForecast forecast = forecasts.get(position);
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
        viewHolder.forecast_weather_image.setImageDrawable(UIUtils.getImageTypeGrey(forecast.forecasts[0].weather[0].main, activity));
        viewHolder.card_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ForecastDetailActivity.class);
                intent.putExtra(Extras.OBJECT_FORECAST_NAME, forecast.city.name);
                activity.startActivity(intent);
            }
        });

    }




    public static class MainViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.forecast_city_name)
        TextView forecast_city_name;
        @InjectView(R.id.forecast_weather_value)
        TextView forecast_weather_value;
        @InjectView(R.id.forecast_weather_description)
        TextView forecast_weather_description;
        @InjectView(R.id.forecast_weather_image)
        ImageView forecast_weather_image;
        @InjectView(R.id.card_content)
        RelativeLayout card_content;

        public MainViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }
}
