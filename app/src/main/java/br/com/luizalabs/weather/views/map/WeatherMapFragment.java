package br.com.luizalabs.weather.views.map;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.luizalabs.App;
import br.com.luizalabs.R;
import br.com.luizalabs.utils.GoogleMapHelper;
import br.com.luizalabs.weather.model.Weather;
import br.com.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.weather.views.SwitchTemperatureUnitEvent;
import io.reactivex.Observable;

public class WeatherMapFragment extends SupportMapFragment implements WeatherMapView {

    @Inject
    WeatherMapPresenter presenter;

    private List<Marker> cityMarkers = new ArrayList<>();
    private Circle centerCircle;

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
            presenter.loadWeather(googleMap);
            googleMap.setInfoWindowAdapter(new InfoWindowAdapter(getContext()));
            googleMap.setOnMarkerClickListener(marker -> {
                marker.showInfoWindow();
                return true;
            });

            googleMap.setOnCameraIdleListener(() -> {
                presenter.onCameraChangePosition(googleMap, getContext());
            });
        });

        presenter.moveMapToLastLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
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
            if (lastLocation != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, GoogleMapHelper.ZOOM_LEVEL));
                drawCircleInCenter(googleMap, lastLocation);
            }
        });
    }

    @Override
    public void createCityMarker(GoogleMap googleMap, Weather weather) {
        LatLng cityLatLong = new LatLng(weather.getLatitude(), weather.getLongitude());
        Marker cityMarker = googleMap.addMarker(new MarkerOptions().position(cityLatLong));
        cityMarker.setTag(weather);
        cityMarkers.add(cityMarker);
    }

    @Override
    public void updateMarkersInfoWindow() {
        Observable.fromIterable(cityMarkers)
                .filter(Marker::isInfoWindowShown)
                .subscribe(Marker::showInfoWindow);
    }

    @Override
    public void removeAllCityMarkers() {
        Observable.fromIterable(cityMarkers).subscribe(Marker::remove);
    }

    @Override
    public void drawCircleInCenter(GoogleMap googleMap, LatLng position) {
        if (centerCircle != null) {
            centerCircle.remove();
        }

        centerCircle = googleMap.addCircle(new CircleOptions()
                .center(position)
                .radius(WeatherInteractor.FIFITY_KM)
                .strokeWidth(1)
                .strokeColor(ContextCompat.getColor(getContext(), R.color.curious_blue))
                .fillColor(ContextCompat.getColor(getContext(), R.color.cornflower_blue)));
    }
}
