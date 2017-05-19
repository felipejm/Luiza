package br.com.luizalabs.luizalabs.weather.views.cards;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import br.com.luizalabs.luizalabs.weather.model.Weather;

public interface WeatherCardsView{
    boolean hasLocationPermission();

    void showLocationRequiredDialog(GoogleApiClient googleApiClient);

    void configureWeatherCards(List<Weather> weathers);
}
