
package br.com.luizalabs.luizalabs.weather.model;

import java.util.List;

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
    public Observable<List<Weather>> getWeather(){
       return weatherApi.getForArea("12,32,15,37,10")
               .map(apiWeathers -> mapper.transform(apiWeathers));
    }
}
