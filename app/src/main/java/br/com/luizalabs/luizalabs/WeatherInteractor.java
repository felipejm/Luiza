package br.com.luizalabs.luizalabs;

import io.reactivex.Observable;

interface WeatherInteractor {
    Observable<Weather> getWeather();
}
