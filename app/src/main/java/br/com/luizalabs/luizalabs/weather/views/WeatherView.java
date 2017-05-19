package br.com.luizalabs.luizalabs.weather.views;

import android.support.v4.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import br.com.luizalabs.luizalabs.weather.model.Weather;

public interface WeatherView {
    void showLocationRequiredDialog(GoogleApiClient googleApiClient);

    boolean hasLocationPermission();

    void onWeatherLoaded();

    void updateFragment(Fragment fragment, boolean fadeAnimation);
}
