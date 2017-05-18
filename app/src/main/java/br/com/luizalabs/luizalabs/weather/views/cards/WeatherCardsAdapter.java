package br.com.luizalabs.luizalabs.weather.views.cards;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.weather.api.ApiWeatherInfo;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherCardsAdapter extends RecyclerView.Adapter<WeatherCardsAdapter.ViewHolder> {

    private List<Weather> weathers;

    public WeatherCardsAdapter(List<Weather> weathers) {
        this.weathers = new ArrayList<>(weathers);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_weather_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Weather weather = weathers.get(position);

        holder.cityName.setText(StringUtils.capitalize(weather.getCityName()));
        holder.temperatureDescription.setText(StringUtils.capitalize(weather.getDescription()));
        holder.temperature.setText(String.format(context.getString(R.string.temperature_format), weather.getTemperature()));
        holder.temperatureMin.setText(String.format(context.getString(R.string.min_temperature_format), weather.getTemperatureMin()));
        holder.temperatureMax.setText(String.format(context.getString(R.string.max_temperature_format), weather.getTemperatureMax()));
        holder.weatherImage.setImageDrawable(ContextCompat.getDrawable(context, weather.getWeatherIconRes()));
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.city_name)
        TextView cityName;

        @BindView(R.id.temperature)
        TextView temperature;

        @BindView(R.id.temperature_description)
        TextView temperatureDescription;

        @BindView(R.id.temperature_min)
        TextView temperatureMin;

        @BindView(R.id.temperature_max)
        TextView temperatureMax;

        @BindView(R.id.weather_image)
        ImageView weatherImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
