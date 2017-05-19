package br.com.luizalabs.luizalabs.weather.views.cards;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;

public interface WeatherCardsPresenter {
    void loadWeathers();

    void configureTemperatureUnit();


}
