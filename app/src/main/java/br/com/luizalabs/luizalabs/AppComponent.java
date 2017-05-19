package br.com.luizalabs.luizalabs;

import javax.inject.Singleton;

import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, InteractorModule.class})
public interface AppComponent {
    void inject(App app);

    WeatherInteractor provideWeatherInteractor();
    UserPreferenceInteractor provideUserPreferenceInteractor();
}
