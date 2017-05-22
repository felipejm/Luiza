package br.com.luizalabs.app;

import javax.inject.Singleton;

import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.user.model.UserPreferencePreferenceInteractorImpl;
import br.com.luizalabs.user.model.UserPreferenceRepository;
import br.com.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.weather.model.WeatherInteractorImpl;
import br.com.luizalabs.weather.model.WeatherMapper;
import br.com.luizalabs.weather.model.WeatherRepository;
import dagger.Module;
import dagger.Provides;

@AppScope
@Module
public class InteractorModule {

    @Provides
    @Singleton
    public WeatherInteractor provideWeatherInteractor(WeatherRepository repository, WeatherMapper mapper) {
        return new WeatherInteractorImpl(repository, mapper);
    }

    @Provides
    @Singleton
    public UserPreferenceInteractor provideUserPreferenceInteractor(UserPreferenceRepository repository) {
        return new UserPreferencePreferenceInteractorImpl(repository);
    }
}
