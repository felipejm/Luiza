package br.com.luizalabs.luizalabs.weather.views.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.App;
import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.utils.DrawableHelper;
import br.com.luizalabs.luizalabs.utils.GoogleApiClientHelper;
import br.com.luizalabs.luizalabs.utils.GoogleMapHelper;
import br.com.luizalabs.luizalabs.utils.LocationHelper;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import br.com.luizalabs.luizalabs.weather.views.SwitchTemperatureUnitEvent;
import br.com.luizalabs.luizalabs.weather.views.cards.DaggerWeatherCardsComponent;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsAdapter;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsModule;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsPresenter;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherMapFragment extends SupportMapFragment implements WeatherMapView {

    public static final int ZOOM_LEVEL = 8;

    @Inject
    WeatherMapPresenter presenter;

    public static WeatherMapFragment newInstance() {
        return new WeatherMapFragment();
    }

    @Override
    public void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);

        DaggerWeatherMapComponent.builder()
                .appComponent(App.getAppComponent())
                .weatherMapModule(new WeatherMapModule(this))
                .build()
                .inject(this);

        getMapAsync(googleMap -> {
            GoogleMapHelper.configureMap(googleMap);
            drawCircleInCenter(googleMap);
            googleMap.setInfoWindowAdapter(new InfoWindowAdapter(getContext()));
            presenter.loadWeather(googleMap);
        });

        presenter.configureGoogleApiClient(getContext());
    }

    @Override
    public void onStart() {
        presenter.connectGoogleApiClient();
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        presenter.diconnectGoogleApiClient();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onSwitchTemperatureUnit(SwitchTemperatureUnitEvent event) {
        getMapAsync(googleMap -> {
            presenter.switchTemperatureUnit();
            googleMap.setInfoWindowAdapter(new InfoWindowAdapter(getContext()));
        });
    }

    @Override
    public void moveMapToMyLocation(LatLng lastLocation) {
        getMapAsync(googleMap -> {
            if(lastLocation != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, ZOOM_LEVEL));
            }
        });
    }

    private void drawCircleInCenter(GoogleMap googleMap) {
        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(googleMap.getCameraPosition().target)
                .radius(50000)
                .strokeWidth(1)
                .strokeColor(ContextCompat.getColor(getContext(), R.color.curious_blue))
                .fillColor(ContextCompat.getColor(getContext(), R.color.cornflower_blue)));

        googleMap.setOnCameraIdleListener(() -> circle.setCenter(googleMap.getCameraPosition().target));
    }
}
