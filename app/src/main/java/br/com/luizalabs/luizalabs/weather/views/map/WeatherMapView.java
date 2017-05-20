package br.com.luizalabs.luizalabs.weather.views.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import br.com.luizalabs.luizalabs.weather.model.Weather;

public interface WeatherMapView {
    void moveMapToMyLocation(LatLng lastLocation);

    void createCityMarker(GoogleMap googleMap, Weather weather);

    void updateMarkersInfoWindow();

    void removeAllCityMarkers();

    void drawCircleInCenter(GoogleMap googleMap, LatLng position);
}
