package br.com.luizalabs.app;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Singleton;

import br.com.luizalabs.user.model.UserPreferencePreferenceRepositoryImpl;
import br.com.luizalabs.user.model.UserPreferenceRepository;
import br.com.luizalabs.weather.api.WeatherApi;
import br.com.luizalabs.weather.model.WeatherRepository;
import br.com.luizalabs.weather.model.WeatherRepositoryImpl;
import dagger.Module;
import dagger.Provides;

@AppScope
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public WeatherRepository provideWeatherRepository(App app, Gson gson,
                                                      SharedPreferences preferences,
                                                      WeatherApi weatherApi) {
        return new WeatherRepositoryImpl(app.getApplicationContext(), gson, weatherApi, preferences);
    }

    @Provides
    @Singleton
    public UserPreferenceRepository provideUserPreferenceRepository(Gson gson,
                                                                    SharedPreferences preferences) {
        return new UserPreferencePreferenceRepositoryImpl(preferences, gson);
    }
}
