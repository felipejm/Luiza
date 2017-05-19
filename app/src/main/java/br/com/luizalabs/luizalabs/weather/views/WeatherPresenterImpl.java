package br.com.luizalabs.luizalabs.weather.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private enum WEATHER_FRAGMENT{
        LOADING, CARDS, MAP
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
    public void configureGoogleApiClient(FragmentActivity activity){
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .enableAutoManage(activity, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void loadWeatherOfLastLocation() {
        showLoadingFragment();

        if (LocationHelper.isLocationAvailable(googleApiClient) && view.hasLocationPermission()) {
            LatLng lastLocation = LocationHelper.getLastLocation(googleApiClient);
            loadWeathers(lastLocation);
        } else {
            view.showLocationRequiredDialog(googleApiClient);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        loadWeatherOfLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void switchFragments() {
        if(currentFragment == WEATHER_FRAGMENT.MAP) {
            currentFragment = WEATHER_FRAGMENT.CARDS;
            view.updateFragment(WeatherCardsFragment.newInstance(), true);
        }else{
            currentFragment = WEATHER_FRAGMENT.MAP;
            view.updateFragment(WeatherMapFragment.newInstance(), true);
        }
    }

    @Override
    public boolean isNotShowingLoadingFragment() {
        return currentFragment != WEATHER_FRAGMENT.LOADING;
    }

    @Override
    public void switchToolbarTemperatureUnitIcon(MenuItem item){
        UserPreference userPreference = userPreferenceInteractor.get();

        if(userPreference.getTemperaturaUnit() == TEMPERATURA_UNIT.CELSIUS){
            item.setIcon(R.drawable.ic_fahrenheit);
            item.setTitle(R.string.change_to_celsius);
            userPreference.setTemperaturaUnit(TEMPERATURA_UNIT.FAHRENHEIT);
        }else{
            item.setIcon(R.drawable.ic_celsius);
            item.setTitle(R.string.change_to_fahrenheit);
            userPreference.setTemperaturaUnit(TEMPERATURA_UNIT.CELSIUS);
        }

        userPreferenceInteractor.save(userPreference);
    }

    @Override
    public void switchToolbarMapListIcon(MenuItem item){
        if(currentFragment == WEATHER_FRAGMENT.CARDS){
            item.setIcon(R.drawable.ic_list);
            item.setTitle(R.string.change_to_list);

        }else{
            item.setIcon(R.drawable.ic_maps);
            item.setTitle(R.string.change_to_map);

        }
    }

    @Override
    public void configureToolbarTemperatureUnitIcon(MenuItem item){
        UserPreference userPreference = userPreferenceInteractor.get();

        if(userPreference.getTemperaturaUnit() == TEMPERATURA_UNIT.CELSIUS){
            item.setIcon(R.drawable.ic_celsius);
            item.setTitle(R.string.change_to_fahrenheit);
        }else{
            item.setIcon(R.drawable.ic_fahrenheit);
            item.setTitle(R.string.change_to_celsius);
        }
    }

    private void loadWeathers(LatLng lastLocation){
        if(lastLocation != null && (interactor.getCache() == null || interactor.getCache().isEmpty())) {
            interactor.getWeatherNearbyLocation(lastLocation)
                    .compose(RxComposer.newThread())
                    .doOnError(throwable -> Log.e("WeatherPresenter", "loadWeathers", throwable))
                    .subscribe(weathers -> {
                        interactor.setCache(weathers);
                        view.onWeatherLoaded();
                        showCardsFragment();
                    });
        }else{
            showCardsFragment();
        }
    }

    private void showCardsFragment() {
        currentFragment = WEATHER_FRAGMENT.CARDS;
        view.updateFragment(WeatherCardsFragment.newInstance(), true);
    }

    private void showLoadingFragment() {
        currentFragment = WEATHER_FRAGMENT.LOADING;
        view.updateFragment(LoadingFragment.newInstance(), false);
    }
}
