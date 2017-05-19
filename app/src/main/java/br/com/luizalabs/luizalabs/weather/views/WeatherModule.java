package br.com.luizalabs.luizalabs.weather.views;

import br.com.luizalabs.luizalabs.AppScope;
import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import dagger.Module;
import dagger.Provides;

@AppScope
@Module
public class WeatherModule {

    private WeatherView view;

    public WeatherModule(WeatherView view) {
        this.view = view;
    }

    @Provides
    public WeatherPresenter proviePresenter(WeatherInteractor interactor,UserPreferenceInteractor userPreferenceInteractor){
        return new WeatherPresenterImpl(userPreferenceInteractor, interactor, view);
    }
}
