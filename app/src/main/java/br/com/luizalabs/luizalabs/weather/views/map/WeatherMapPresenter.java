package br.com.luizalabs.luizalabs.weather.views.map;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;

public interface WeatherMapPresenter {

    void loadWeather(GoogleMap googleMap);

    void configureGoogleApiClient(Context context);

    void connectGoogleApiClient();

    void diconnectGoogleApiClient();

    void switchTemperatureUnit();

    void moveMapToLastLocation();
}
