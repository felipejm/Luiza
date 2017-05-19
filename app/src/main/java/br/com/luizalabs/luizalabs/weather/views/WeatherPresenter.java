package br.com.luizalabs.luizalabs.weather.views;

import android.content.Context;
import android.location.Location;
import android.view.MenuItem;

public interface WeatherPresenter {
    void loadWeathers();

    void showLoadingFragment();

    void showCardsFragment();

    void switchFragments();

    void switchToolbarTemperatureUnitIcon(MenuItem item);

    void switchToolbarMapListIcon(MenuItem item);

    void configureToolbarTemperatureUnitIcon(MenuItem item);

    boolean isNotShowingLoadingFragment();
}
