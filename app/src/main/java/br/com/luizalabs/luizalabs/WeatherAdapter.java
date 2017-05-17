package br.com.luizalabs.luizalabs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<ApiWeatherInfo> apiWeatherInfos;

    public WeatherAdapter(List<ApiWeatherInfo> apiWeatherInfos) {
        this.apiWeatherInfos = new ArrayList<>(apiWeatherInfos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_weather_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return apiWeatherInfos.size();
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
