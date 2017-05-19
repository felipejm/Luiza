package br.com.luizalabs.luizalabs.weather.views.map;

import br.com.luizalabs.luizalabs.AppScope;
import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsPresenter;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsPresenterImpl;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsView;
import dagger.Module;
import dagger.Provides;

@AppScope
@Module
public class WeatherMapModule {

    private WeatherMapView view;

    public WeatherMapModule(WeatherMapView view) {
        this.view = view;
    }

    @Provides
    public WeatherMapPresenter proviePresenter(UserPreferenceInteractor userPreferenceInteractor, WeatherInteractor interactor){
        return new WeatherMapPresenterImpl(userPreferenceInteractor, interactor, view);
    }
}
