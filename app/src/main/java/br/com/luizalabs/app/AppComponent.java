package br.com.luizalabs.app;

import javax.inject.Singleton;

import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.user.model.UserPreferenceRepository;
import br.com.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.weather.model.WeatherRepository;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, InteractorModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(App app);

    WeatherRepository provideWeatherRepository();
    UserPreferenceRepository provideUserPreferenceRepository();
    WeatherInteractor provideWeatherInteractor();
    UserPreferenceInteractor provideUserPreferenceInteractor();
}
