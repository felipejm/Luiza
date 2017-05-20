package br.com.luizalabs.weather.views.map;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import br.com.luizalabs.user.model.UserPreference;
import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.utils.NetworkHelper;
import br.com.luizalabs.utils.RxComposer;
import br.com.luizalabs.weather.model.Weather;
import br.com.luizalabs.weather.model.WeatherInteractor;
import io.reactivex.Observable;

public class WeatherMapPresenterImpl implements WeatherMapPresenter {

    private UserPreferenceInteractor userPreferenceInteractor;
    private WeatherInteractor interactor;
    private WeatherMapView view;

    private List<Weather> weathers;

    public WeatherMapPresenterImpl(UserPreferenceInteractor userPreferenceInteractor,
                                   WeatherInteractor interactor, WeatherMapView view) {
        this.userPreferenceInteractor = userPreferenceInteractor;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void loadWeather(GoogleMap googleMap) {
        UserPreference userPreference = userPreferenceInteractor.get();
        interactor.getCache()
                .compose(RxComposer.newThread())
                .doOnNext(weathers -> this.weathers = weathers)
                .flatMapIterable(weathers -> weathers)
                .doOnNext(weather -> weather.changeTemperatureUnit(userPreference.getTemperaturaUnit()))
                .subscribe(weather -> view.createCityMarker(googleMap, weather));
    }

    @Override
    public void loadWeatherFromLocation(GoogleMap googleMap, LatLng location) {
        UserPreference userPreference = userPreferenceInteractor.get();
        interactor.getFromLocation(location)
                .compose(RxComposer.newThread())
                .doOnNext(weathers -> {
                    this.weathers = weathers;
                    this.interactor.setCache(weathers);
                }).flatMapIterable(weathers -> weathers)
                .doOnNext(weather -> weather.changeTemperatureUnit(userPreference.getTemperaturaUnit()))
                .subscribe(weather -> view.createCityMarker(googleMap, weather), throwable -> Log.e("WeatherPresenter", "loadWeathers", throwable));
    }

    @Override
    public void onCameraChangePosition(GoogleMap googleMap, Context context) {
        if (NetworkHelper.isNetworkAvailable(context)) {
            LatLng currentLocation = googleMap.getCameraPosition().target;
            view.removeAllCityMarkers();
            view.drawCircleInCenter(googleMap, currentLocation);

            saveLastLocation(currentLocation);
            loadWeatherFromLocation(googleMap, currentLocation);
        }
    }

    @Override
    public void saveLastLocation(LatLng lastLocation) {
        userPreferenceInteractor.saveLastLocation(lastLocation.latitude, lastLocation.longitude);
    }

    @Override
    public void switchTemperatureUnit() {
        UserPreference userPreference = userPreferenceInteractor.get();
        if (weathers != null) {
            Observable.fromIterable(weathers).forEach(weather -> weather.changeTemperatureUnit(userPreference.getTemperaturaUnit()));
            view.updateMarkersInfoWindow();
        }
    }

    @Override
    public void moveMapToLastLocation() {
        UserPreference userPreference = userPreferenceInteractor.get();
        LatLng lastLocation = new LatLng(userPreference.getLastLocationLatitude(), userPreference.getLastLocationLongitude());
        view.moveMapToMyLocation(lastLocation);
    }
}
