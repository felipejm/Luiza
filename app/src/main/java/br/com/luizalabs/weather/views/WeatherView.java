package br.com.luizalabs.weather.views;

import android.support.v4.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;

public interface WeatherView {
    void showLocationRequiredDialog(GoogleApiClient googleApiClient);

    boolean hasLocationPermission();

    void onWeatherLoaded();

    void updateFragment(Fragment fragment, boolean fadeAnimation);
}
