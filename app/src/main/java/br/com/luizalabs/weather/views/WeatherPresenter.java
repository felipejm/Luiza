package br.com.luizalabs.weather.views;

import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

public interface WeatherPresenter {

    void loadWeatherFromLocation(FragmentActivity fragmentActivity);

    void loadWeatherFromLocationService();

    void switchFragments();

    void switchToolbarTemperatureUnitIcon(MenuItem item);

    void switchToolbarMapListIcon(MenuItem item);

    void configureToolbarTemperatureUnitIcon(MenuItem item);

    boolean isNotShowingLoadingFragment();

    void showLoadingFragment();
}
