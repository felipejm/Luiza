package br.com.luizalabs.luizalabs.weather.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;

public interface WeatherInteractor {
    Observable<List<Weather>> getWeather();

    Observable<List<Weather>> getWeatherNearbyLocation(LatLng location);

    void setCache(List<Weather> weathers);

    List<Weather> getCache();
}
