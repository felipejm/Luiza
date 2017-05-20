package br.com.luizalabs.weather.views.cards;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import br.com.luizalabs.user.model.UserPreference;
import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.utils.LocationHelper;
import br.com.luizalabs.weather.model.Weather;
import br.com.luizalabs.weather.model.WeatherInteractor;
import io.reactivex.Observable;

public class WeatherCardsPresenterImpl implements WeatherCardsPresenter {

    private UserPreferenceInteractor userPreferenceInteractor;
    private WeatherInteractor interactor;
    private WeatherCardsView view;

    private List<Weather> weathers;

    public WeatherCardsPresenterImpl(UserPreferenceInteractor userPreferenceInteractor,
                                     WeatherInteractor interactor, WeatherCardsView view) {
        this.userPreferenceInteractor = userPreferenceInteractor;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void loadWeathers() {
        UserPreference userPreference = userPreferenceInteractor.get();
        LatLng currentLatLng = new LatLng(userPreference.getLastLocationLatitude(), userPreference.getLastLocationLongitude());

        interactor.getCache()
                .flatMapIterable(weather1 -> weather1)
                .sorted((weather1, weather2) -> compareToDistance(currentLatLng, weather1, weather2))
                .toList().toObservable()
                .doOnNext(weathers -> this.weathers = weathers)
                .subscribe(weathers -> {
                    configureTemperatureUnit();
                    view.configureWeatherCards((weathers));
                });
    }

    @Override
    public void configureTemperatureUnit() {
        UserPreference userPreference = userPreferenceInteractor.get();
        Observable.fromIterable(weathers).forEach(weather -> weather.changeTemperatureUnit(userPreference.getTemperaturaUnit()));
    }

    private int compareToDistance(LatLng currentLatLng, Weather weather1, Weather weather2) {
        LatLng weather1LatLng = new LatLng(weather1.getLatitude(), weather1.getLongitude());
        LatLng weather2LatLng = new LatLng(weather2.getLatitude(), weather2.getLongitude());
        float distancePredictions1 = LocationHelper.distanceBetween(weather1LatLng, currentLatLng);
        float distancePredictions2 = LocationHelper.distanceBetween(weather2LatLng, currentLatLng);
        return distancePredictions1 > distancePredictions2 ? 1 : -1;
    }
}
