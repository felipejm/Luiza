package br.com.luizalabs.weather.model;

import com.google.android.gms.maps.model.LatLng;

import br.com.luizalabs.weather.api.ApiWeathers;
import io.reactivex.Observable;

public interface WeatherRepository {
    Observable<ApiWeathers> listByLocation(LatLng location);
}
