package br.com.luizalabs.luizalabs.weather.model;

import io.reactivex.Observable;

public interface WeatherInteractor {
    Observable<Weather> getWeather();
}
