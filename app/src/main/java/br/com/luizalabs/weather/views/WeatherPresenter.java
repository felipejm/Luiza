package br.com.luizalabs.weather.views;

import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.google.android.gms.location.LocationListener;

public interface WeatherPresenter {

    void loadWeatherByLastLocation(FragmentActivity fragmentActivity);

    void loadWeatherWithLocationService(FragmentActivity fragmentActivity);

    void removeRequestLocationUpdate(LocationListener locationListener);

    void switchFragments();

    void switchToolbarTemperatureUnitIcon(MenuItem item);

    void switchToolbarMapListIcon(MenuItem item);

    void configureToolbarTemperatureUnitIcon(MenuItem item);

    boolean isNotShowingLoadingFragment();

    void showLoadingFragment();
}
