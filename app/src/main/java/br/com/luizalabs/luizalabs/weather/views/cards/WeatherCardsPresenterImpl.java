package br.com.luizalabs.luizalabs.weather.views.cards;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;

import br.com.luizalabs.luizalabs.utils.RxComposer;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;

public class WeatherCardsPresenterImpl extends AppCompatActivity  implements WeatherCardsPresenter {

    private WeatherInteractor interactor;
    private WeatherCardsView view;

    public WeatherCardsPresenterImpl(WeatherInteractor interactor, WeatherCardsView view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void loadWeather(){
        interactor.getWeather().compose(RxComposer.newThread()).subscribe(weather -> {
            view.configureWeatherCards(Arrays.asList(weather));
        }, throwable -> {
            Log.e("WeatherCardsPresenter","loadWeather",throwable);
        });
    }
}
