package br.com.luizalabs.luizalabs.weather.views.cards;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.App;
import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.utils.LocationHelper;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import br.com.luizalabs.luizalabs.weather.views.SwitchTemperatureUnitEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherCardsFragment extends Fragment implements WeatherCardsView {

    public static final int PERMISSION_REQUEST_CODE = 101;

    private WeatherCardsAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    WeatherCardsPresenter presenter;

    public static WeatherCardsFragment newInstance() {
        WeatherCardsFragment fragment = new WeatherCardsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_cards, container, false);
        ButterKnife.bind(this, view);

        DaggerWeatherCardsComponent.builder()
                .appComponent(App.getAppComponent())
                .weatherCardsModule(new WeatherCardsModule(this))
                .build()
                .inject(this);

        presenter.configureGoogleApiClient(getContext());
        return view;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        presenter.connectGoogleApiClient();
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        presenter.disconnectGoogleApiClient();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.loadWeatherOfLastLocation();
    }

    @Subscribe(sticky = true)
    public void onSwitchTemperatureUnit(SwitchTemperatureUnitEvent event){
        presenter.configureTemperatureUnit();
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLocationRequiredDialog(GoogleApiClient googleApiClient){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(R.string.dialog_need_location_title);
        builder.setMessage(R.string.dialog_need_location_message);
        builder.setPositiveButton(R.string.button_active, (dialog, which) -> {
            enableLocation(googleApiClient);
            dialog.dismiss();
        });

        builder.show();
    }

    @Override
    public void configureWeatherCards(List<Weather> weathers) {
        adapter = new WeatherCardsAdapter(weathers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void enableLocation(GoogleApiClient googleApiClient) {
        if(!LocationHelper.isLocationAvailable(googleApiClient)){
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        }else if(!hasLocationPermission()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }
}
