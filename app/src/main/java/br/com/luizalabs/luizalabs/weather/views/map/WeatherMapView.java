package br.com.luizalabs.luizalabs.weather.views.map;

import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import br.com.luizalabs.luizalabs.weather.model.Weather;

public interface WeatherMapView {
    void moveMapToMyLocation(LatLng lastLocation);

    void showLocationRequiredDialog();

    boolean hasLocationPermission();
}
