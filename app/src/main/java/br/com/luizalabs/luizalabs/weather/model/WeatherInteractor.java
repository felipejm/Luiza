package br.com.luizalabs.luizalabs.weather.model;

import java.util.List;

import io.reactivex.Observable;

public interface WeatherInteractor {
    Observable<List<Weather>> getWeather();
}
