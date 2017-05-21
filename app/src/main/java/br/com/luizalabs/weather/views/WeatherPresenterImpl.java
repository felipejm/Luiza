package br.com.luizalabs.weather.views;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.luizalabs.R;
import br.com.luizalabs.user.model.UserPreference;
import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.utils.LocationHelper;
import br.com.luizalabs.utils.RxComposer;
import br.com.luizalabs.weather.model.TemperatureUnitEnum;
import br.com.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.weather.views.cards.WeatherCardsFragment;
import br.com.luizalabs.weather.views.map.WeatherMapFragment;

public class WeatherPresenterImpl implements WeatherPresenter {

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
    public void loadWeatherByLastLocation(FragmentActivity fragmentActivity) {
        if (userPreferenceInteractor.hasLastLocation()) {
            UserPreference userPreference = userPreferenceInteractor.get();
            LatLng lastLocation = new LatLng(userPreference.getLastLocationLatitude(), userPreference.getLastLocationLongitude());
            loadWeathers(lastLocation);
        } else {
            configureGoogleApiClient(fragmentActivity);
        }
    }

    @Override
    public void loadWeatherWithLocationService(FragmentActivity fragmentActivity) {
        if (LocationHelper.isLocationAvailable(googleApiClient) && view.hasLocationPermission()) {
            LatLng lastLocation = LocationHelper.getLastLocation(googleApiClient);
            loadWeathers(lastLocation);
            userPreferenceInteractor.saveLastLocation(lastLocation.latitude, lastLocation.longitude);

        } else if(!view.hasLocationPermission()){
            view.showLocationRequiredDialog(googleApiClient);

        }else{
            LocationHelper.requestLocation(googleApiClient, fragmentActivity, view);
        }
    }

    @Override
    public void removeRequestLocationUpdate(LocationListener locationListener){
        LocationHelper.removeLocationRequestUpdate(googleApiClient, locationListener);
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

        if (userPreference.getTemperaturaUnit() == TemperatureUnitEnum.CELSIUS) {
            item.setIcon(R.drawable.ic_fahrenheit);
            item.setTitle(R.string.change_to_celsius);
            userPreferenceInteractor.saveTemperatureUnit(TemperatureUnitEnum.FAHRENHEIT);
        } else {
            item.setIcon(R.drawable.ic_celsius);
            item.setTitle(R.string.change_to_fahrenheit);
            userPreferenceInteractor.saveTemperatureUnit(TemperatureUnitEnum.CELSIUS);
        }
    }

    @Override
    public void configureToolbarTemperatureUnitIcon(MenuItem item) {
        UserPreference userPreference = userPreferenceInteractor.get();

        if (userPreference.getTemperaturaUnit() == TemperatureUnitEnum.CELSIUS) {
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
                    .enableAutoManage(activity, view)
                    .addConnectionCallbacks(view)
                    .addOnConnectionFailedListener(view)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void loadWeathers(LatLng lastLocation) {
        interactor.listByLocation(lastLocation)
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

    private enum WEATHER_FRAGMENT {
        LOADING, EMPTY, CARDS, MAP
    }
}
