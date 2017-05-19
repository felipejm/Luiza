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
import br.com.luizalabs.luizalabs.utils.LocationHelper;
import br.com.luizalabs.luizalabs.utils.RxComposer;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import io.reactivex.Observable;

public class WeatherCardsPresenterImpl implements WeatherCardsPresenter{

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
}
