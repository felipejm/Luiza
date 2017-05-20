package br.com.luizalabs.luizalabs.weather.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;

public interface WeatherInteractor {
    int FIFITY_KM = 50000;

    Observable<List<Weather>> getWeatherOfLocation(LatLng location);

    void setCache(List<Weather> weathers);

    Observable<List<Weather>> getCache();
}
