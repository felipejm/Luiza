package br.com.luizalabs.luizalabs.weather.views.map;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.List;

import br.com.luizalabs.luizalabs.user.model.UserPreference;
import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsPresenter;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsView;
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
}
