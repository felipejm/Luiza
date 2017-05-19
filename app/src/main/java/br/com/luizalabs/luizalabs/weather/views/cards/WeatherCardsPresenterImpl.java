package br.com.luizalabs.luizalabs.weather.views.cards;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.List;

import br.com.luizalabs.luizalabs.user.model.UserPreference;
import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.utils.RxComposer;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import io.reactivex.Observable;

public class WeatherCardsPresenterImpl implements WeatherCardsPresenter,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private UserPreferenceInteractor userPreferenceInteractor;
    private WeatherInteractor interactor;
    private WeatherCardsView view;

    private GoogleApiClient googleApiClient;
    private List<Weather> weathers;

    public WeatherCardsPresenterImpl(UserPreferenceInteractor userPreferenceInteractor,
                                     WeatherInteractor interactor, WeatherCardsView view) {
        this.userPreferenceInteractor = userPreferenceInteractor;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void loadWeathers(){
        this.weathers = interactor.getCache();
        configureTemperatureUnit();
        view.configureWeatherCards((weathers));
    }

    @Override
    public void configureTemperatureUnit(){
        UserPreference userPreference = userPreferenceInteractor.get();
        Observable.fromIterable(weathers).forEach(weather -> weather.changeTemperatureUnit(userPreference.getTemperaturaUnit()));
    }

    @Override
    public void configureGoogleApiClient(Context context){
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void connectGoogleApiClient(){
        googleApiClient.connect();
    }

    @Override
    public void disconnectGoogleApiClient(){
        googleApiClient.connect();
    }

    @Override
    public Location getLastPosition(){
        Location lastLocation = null;
       /* if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }*/

        return lastLocation;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location lastPosition = getLastPosition();

        if(lastPosition != null) {
            int distance = 50000;
            LatLng latLng = new LatLng(lastPosition.getLatitude(), lastPosition.getLongitude());
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            LatLngBounds initialBounds = builder.include(latLng).build();

            LatLng topRight = SphericalUtil.computeOffset(initialBounds.northeast, distance * Math.sqrt(2), 45);
            LatLng bottomRight = SphericalUtil.computeOffset(initialBounds.northeast, distance * Math.sqrt(2), 135);
            LatLng bottomLeft = SphericalUtil.computeOffset(initialBounds.southwest, distance * Math.sqrt(2), 225);
            LatLng topLeft = SphericalUtil.computeOffset(initialBounds.southwest, distance * Math.sqrt(2), 315);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
