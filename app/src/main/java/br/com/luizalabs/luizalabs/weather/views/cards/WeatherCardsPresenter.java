package br.com.luizalabs.luizalabs.weather.views.cards;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;

public interface WeatherCardsPresenter {
    void loadWeather();
    void configureGoogleApiClient();

    void connectGoogleApiClient();

    void disconnectGoogleApiClient();

    Location getLastPosition();
}
