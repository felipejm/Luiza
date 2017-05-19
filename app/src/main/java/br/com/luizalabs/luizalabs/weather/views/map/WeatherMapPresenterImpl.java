package br.com.luizalabs.luizalabs.weather.views.map;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import br.com.luizalabs.luizalabs.user.model.UserPreference;
import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.utils.GoogleApiClientHelper;
import br.com.luizalabs.luizalabs.utils.LocationHelper;
import br.com.luizalabs.luizalabs.utils.RxComposer;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsPresenter;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsView;
import io.reactivex.Observable;

public class WeatherMapPresenterImpl implements WeatherMapPresenter,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private UserPreferenceInteractor userPreferenceInteractor;
    private WeatherInteractor interactor;
    private WeatherMapView view;

    private GoogleApiClient googleApiClient;

    private List<Weather> weathers;
    private List<Marker> cityMarkers = new ArrayList<>();

    public WeatherMapPresenterImpl(UserPreferenceInteractor userPreferenceInteractor,
                                   WeatherInteractor interactor, WeatherMapView view) {
        this.userPreferenceInteractor = userPreferenceInteractor;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void loadWeather(GoogleMap googleMap) {
        UserPreference userPreference = userPreferenceInteractor.get();
        weathers = interactor.getCache();

        Observable.fromIterable(weathers)
                .compose(RxComposer.newThread())
                .doOnNext(weather -> weather.changeTemperatureUnit(userPreference.getTemperaturaUnit()))
                .subscribe(weather -> createCityMarker(googleMap, weather));
    }

    @Override
    public void configureGoogleApiClient(Context context) {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClientHelper.create(context, this, this);
        }
    }

    @Override
    public void connectGoogleApiClient() {
        googleApiClient.connect();
    }

    @Override
    public void diconnectGoogleApiClient() {
        googleApiClient.connect();
    }

    @Override
    public void switchTemperatureUnit(){
        UserPreference userPreference = userPreferenceInteractor.get();
        Observable.fromIterable(weathers).forEach(weather -> weather.changeTemperatureUnit(userPreference.getTemperaturaUnit()));
        updateMarkersInfoWindow();
    }

    @Override
    public void moveMapToLastLocation() {
        LatLng lastLocation = LocationHelper.getLastLocation(googleApiClient);
        view.moveMapToMyLocation(lastLocation);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        moveMapToLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void updateMarkersInfoWindow(){
        Observable.fromIterable(cityMarkers)
                .filter(Marker::isInfoWindowShown)
                .subscribe(Marker::showInfoWindow);
    }

    private void createCityMarker(GoogleMap googleMap, Weather weather) {
        LatLng cityLatLong = new LatLng(weather.getLatitude(), weather.getLongitude());
        Marker cityMarker = googleMap.addMarker(new MarkerOptions().position(cityLatLong));
        cityMarker.setTag(weather);
        cityMarkers.add(cityMarker);
    }
}
