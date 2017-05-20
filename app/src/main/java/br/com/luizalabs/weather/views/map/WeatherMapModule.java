package br.com.luizalabs.weather.views.map;

import br.com.luizalabs.AppScope;
import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.weather.model.WeatherInteractor;
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
    public WeatherMapPresenter proviePresenter(UserPreferenceInteractor userPreferenceInteractor, WeatherInteractor interactor) {
        return new WeatherMapPresenterImpl(userPreferenceInteractor, interactor, view);
    }
}
