package br.com.luizalabs.luizalabs.weather.views;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;

public interface WeatherPresenter {
    void configureGoogleApiClient(FragmentActivity context);


    void loadWeatherOfLastLocation();

    void switchFragments();

    void switchToolbarTemperatureUnitIcon(MenuItem item);

    void switchToolbarMapListIcon(MenuItem item);

    void configureToolbarTemperatureUnitIcon(MenuItem item);

    boolean isNotShowingLoadingFragment();
}
