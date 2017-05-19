package br.com.luizalabs.luizalabs.weather.views.cards;

import br.com.luizalabs.luizalabs.AppScope;
import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
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
    public WeatherCardsPresenter proviePresenter(UserPreferenceInteractor userPreferenceInteractor, WeatherInteractor interactor){
        return new WeatherCardsPresenterImpl(userPreferenceInteractor, interactor, view);
    }
}
