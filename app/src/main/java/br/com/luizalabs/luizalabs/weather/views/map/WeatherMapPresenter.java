package br.com.luizalabs.luizalabs.weather.views.map;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface WeatherMapPresenter {

    void loadWeather(GoogleMap googleMap);

    void onCameraChangePosition(GoogleMap googleMap, Context context);

    void saveLastLocation(LatLng lastLocation);

    void loadWeatherFromLocation(GoogleMap googleMap, LatLng location);

    void switchTemperatureUnit();

    void moveMapToLastLocation();
}
