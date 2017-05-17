
package br.com.luizalabs.luizalabs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

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
       return weatherApi.getForCity(6320527, BuildConfig.WEATHER_API_KEY)
               .map(apiWeather -> mapper.transform(apiWeather));
    }
}
