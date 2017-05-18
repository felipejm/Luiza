package br.com.luizalabs.luizalabs;

import br.com.luizalabs.luizalabs.weather.api.WeatherApi;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractorImpl;
import br.com.luizalabs.luizalabs.weather.model.WeatherMapper;
import dagger.Module;
import dagger.Provides;

@AppScope
@Module
public class InteractorModule {

    @Provides
    public WeatherInteractor provideWeatherInteractor(WeatherApi weatherApi, WeatherMapper mapper){
        return new WeatherInteractorImpl(weatherApi, mapper);
    }
}
