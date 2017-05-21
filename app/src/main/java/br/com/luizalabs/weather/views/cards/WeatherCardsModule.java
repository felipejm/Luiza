package br.com.luizalabs.weather.views.cards;

import br.com.luizalabs.app.AppScope;
import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.weather.model.WeatherInteractor;
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
    public WeatherCardsPresenter proviePresenter(UserPreferenceInteractor userPreferenceInteractor, WeatherInteractor interactor) {
        return new WeatherCardsPresenterImpl(userPreferenceInteractor, interactor, view);
    }
}
