package br.com.luizalabs.luizalabs.weather.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.user.model.UserPreference;
import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.utils.LocationHelper;
import br.com.luizalabs.luizalabs.utils.RxComposer;
import br.com.luizalabs.luizalabs.weather.model.TEMPERATURA_UNIT;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsFragment;
import br.com.luizalabs.luizalabs.weather.views.map.WeatherMapFragment;

public class WeatherPresenterImpl implements WeatherPresenter,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private enum WEATHER_FRAGMENT {
        LOADING, EMPTY, CARDS, MAP
    }

    private UserPreferenceInteractor userPreferenceInteractor;
    private WeatherInteractor interactor;
    private WeatherView view;

    private GoogleApiClient googleApiClient;
    private WEATHER_FRAGMENT currentFragment;

    public WeatherPresenterImpl(UserPreferenceInteractor userPreferenceInteractor,
                                WeatherInteractor interactor, WeatherView view) {
        this.userPreferenceInteractor = userPreferenceInteractor;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void loadWeatherFromLocation(FragmentActivity fragmentActivity) {
        if (userPreferenceInteractor.hasLastLocation()) {
            UserPreference userPreference = userPreferenceInteractor.get();
            LatLng lastLocation = new LatLng(userPreference.getLastLocationLatitude(), userPreference.getLastLocationLongitude());
            loadWeathers(lastLocation);
        } else {
            configureGoogleApiClient(fragmentActivity);
        }
    }

    @Override
    public void loadWeatherFromLocationService() {
        if (LocationHelper.isLocationAvailable(googleApiClient) && view.hasLocationPermission()) {
            LatLng lastLocation = LocationHelper.getLastLocation(googleApiClient);
            loadWeathers(lastLocation);
            userPreferenceInteractor.saveLastLocation(lastLocation.latitude, lastLocation.longitude);

        } else {
            view.showLocationRequiredDialog(googleApiClient);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        loadWeatherFromLocationService();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void switchFragments() {
        if (currentFragment == WEATHER_FRAGMENT.MAP) {
            currentFragment = WEATHER_FRAGMENT.CARDS;
            view.updateFragment(WeatherCardsFragment.newInstance(), true);
        } else {
            currentFragment = WEATHER_FRAGMENT.MAP;
            view.updateFragment(WeatherMapFragment.newInstance(), true);
        }
    }

    @Override
    public boolean isNotShowingLoadingFragment() {
        return currentFragment != WEATHER_FRAGMENT.LOADING;
    }

    @Override
    public void switchToolbarMapListIcon(MenuItem item) {
        if (currentFragment == WEATHER_FRAGMENT.CARDS) {
            item.setIcon(R.drawable.ic_list);
            item.setTitle(R.string.change_to_list);

        } else {
            item.setIcon(R.drawable.ic_maps);
            item.setTitle(R.string.change_to_map);
        }
    }

    @Override
    public void switchToolbarTemperatureUnitIcon(MenuItem item) {
        UserPreference userPreference = userPreferenceInteractor.get();

        if (userPreference.getTemperaturaUnit() == TEMPERATURA_UNIT.CELSIUS) {
            item.setIcon(R.drawable.ic_fahrenheit);
            item.setTitle(R.string.change_to_celsius);
            userPreferenceInteractor.saveTemperatureUnit(TEMPERATURA_UNIT.FAHRENHEIT);
        } else {
            item.setIcon(R.drawable.ic_celsius);
            item.setTitle(R.string.change_to_fahrenheit);
            userPreferenceInteractor.saveTemperatureUnit(TEMPERATURA_UNIT.CELSIUS);
        }
    }

    @Override
    public void configureToolbarTemperatureUnitIcon(MenuItem item) {
        UserPreference userPreference = userPreferenceInteractor.get();

        if (userPreference.getTemperaturaUnit() == TEMPERATURA_UNIT.CELSIUS) {
            item.setIcon(R.drawable.ic_celsius);
            item.setTitle(R.string.change_to_fahrenheit);
        } else {
            item.setIcon(R.drawable.ic_fahrenheit);
            item.setTitle(R.string.change_to_celsius);
        }
    }

    @Override
    public void showLoadingFragment() {
        currentFragment = WEATHER_FRAGMENT.LOADING;
        view.updateFragment(LoadingFragment.newInstance(), false);
    }

    private void configureGoogleApiClient(FragmentActivity activity) {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .enableAutoManage(activity, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void loadWeathers(LatLng lastLocation) {
        interactor.getWeatherOfLocation(lastLocation)
                .compose(RxComposer.newThread())
                .subscribe(weathers -> {
                    if (weathers.isEmpty()) {
                        showEmptyFragment();

                    } else {
                        interactor.setCache(weathers);
                        view.onWeatherLoaded();
                        showCardsFragment();
                    }
                }, throwable -> {
                    showEmptyFragment();
                    Log.e("WeatherPresenter", "loadWeathers", throwable);
                });
    }

    private void showCardsFragment() {
        currentFragment = WEATHER_FRAGMENT.CARDS;
        view.updateFragment(WeatherCardsFragment.newInstance(), true);
    }

    private void showEmptyFragment() {
        currentFragment = WEATHER_FRAGMENT.EMPTY;
        view.updateFragment(EmptyFragment.newInstance(), true);
    }
}
