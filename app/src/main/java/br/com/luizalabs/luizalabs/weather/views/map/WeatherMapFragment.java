package br.com.luizalabs.luizalabs.weather.views.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    public static final int PERMISSION_REQUEST_CODE = 101;

    @Inject
    WeatherMapPresenter presenter;

    private GoogleApiClient googleApiClient;

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

        getMapAsync(GoogleMapHelper::configureMap);
        getMapAsync(googleMap -> {
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
    public boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLocationRequiredDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(R.string.dialog_need_location_title);
        builder.setMessage(R.string.dialog_need_location_message);
        builder.setPositiveButton(R.string.button_active, (dialog, which) -> {
            enableLocation();
            dialog.dismiss();
        });

        builder.show();
    }

    @Override
    public void moveMapToMyLocation(LatLng lastLocation) {
        getMapAsync(googleMap -> {
            if(lastLocation != null) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, ZOOM_LEVEL));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.moveMapToLastLocation();
    }

    private void enableLocation() {
        if(!LocationHelper.isLocationAvailable(googleApiClient)){
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        }else if(!hasLocationPermission()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }
}
