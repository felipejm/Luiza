package br.com.luizalabs.luizalabs.weather.views.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import br.com.luizalabs.luizalabs.AppScope;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import dagger.Module;
import dagger.Provides;

@AppScope
@Module
public class WeatherCardsModule {

    private WeatherCardsView view;

    public WeatherCardsModule(WeatherCardsView view) {
        this.view = view;
    }

    @Provides
    public WeatherCardsPresenter proviePresenter(WeatherInteractor interactor){
        return new WeatherCardsPresenterImpl(interactor, view);
    }
}
