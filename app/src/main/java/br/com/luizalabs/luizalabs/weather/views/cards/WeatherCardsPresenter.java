package br.com.luizalabs.luizalabs.weather.views.cards;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;

public interface WeatherCardsPresenter {
    void loadWeather();

    void configureGoogleApiClient(Context context);

    void connectGoogleApiClient();

    void disconnectGoogleApiClient();

    Location getLastPosition();
}
