
package br.com.luizalabs.luizalabs.weather.model;

import java.util.Locale;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.BuildConfig;
import br.com.luizalabs.luizalabs.weather.api.WeatherApi;
import io.reactivex.Observable;

public class WeatherInteractorImpl implements WeatherInteractor {

    private WeatherApi weatherApi;
    private WeatherMapper mapper;

    public WeatherInteractorImpl(WeatherApi weatherApi, WeatherMapper mapper) {
        this.weatherApi = weatherApi;
        this.mapper = mapper;
    }

    @Override
    public Observable<Weather> getWeather(){
       return weatherApi.getForCity(6320527)
               .map(apiWeather -> mapper.transform(apiWeather));
    }
}
