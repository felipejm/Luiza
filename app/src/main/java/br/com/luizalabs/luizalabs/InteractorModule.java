package br.com.luizalabs.luizalabs;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Singleton;

import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.user.model.UserPreferencePreferenceInteractorImpl;
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
    @Singleton
    public WeatherInteractor provideWeatherInteractor(App app,Gson gson, SharedPreferences preferences, WeatherApi weatherApi, WeatherMapper mapper){
        return new WeatherInteractorImpl(app.getApplicationContext(), gson, preferences, weatherApi, mapper);
    }

    @Provides
    @Singleton
    public UserPreferenceInteractor provideUserPreferenceInteractor(Gson gson, SharedPreferences sharedPreferences){
        return new UserPreferencePreferenceInteractorImpl(gson, sharedPreferences);
    }
}
