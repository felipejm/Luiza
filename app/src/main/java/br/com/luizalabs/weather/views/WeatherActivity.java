package br.com.luizalabs.weather.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import br.com.luizalabs.R;
import br.com.luizalabs.app.App;
import br.com.luizalabs.utils.LocationHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements WeatherView {

    public static final int PERMISSION_REQUEST_CODE = 101;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        DaggerWeatherComponent.builder()
                .appComponent(App.getAppComponent())
                .weatherModule(new WeatherModule(this))
                .build()
                .inject(this);

        presenter.showLoadingFragment();
        presenter.loadWeatherByLastLocation(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.loadWeatherWithLocationService(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LocationHelper.LOCATION_SETTING_REQUEST_CODE){
            presenter.loadWeatherWithLocationService(this);
        }
    }

    @Override
    public void showLocationRequiredDialog(GoogleApiClient googleApiClient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.dialog_need_location_title);
        builder.setMessage(R.string.dialog_need_location_message);
        builder.setPositiveButton(R.string.button_active, (dialog, which) -> {
            enableLocation(googleApiClient);
            dialog.dismiss();
        });

        builder.show();
    }

    private void enableLocation(GoogleApiClient googleApiClient) {
        if (!LocationHelper.isLocationAvailable(googleApiClient)) {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        } else if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onWeatherLoaded() {
        invalidateOptionsMenu();
    }

    @Override
    public void updateFragment(Fragment fragment, boolean fadeAnimation) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (fadeAnimation) {
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (presenter.isNotShowingLoadingFragment()) {
            getMenuInflater().inflate(R.menu.menu, menu);
            presenter.configureToolbarTemperatureUnitIcon(menu.findItem(R.id.action_unit));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_unit:
                presenter.switchToolbarTemperatureUnitIcon(item);
                EventBus.getDefault().postSticky(new SwitchTemperatureUnitEvent());
                return true;

            case R.id.action_map:
                presenter.switchToolbarMapListIcon(item);
                presenter.switchFragments();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        presenter.loadWeatherWithLocationService(this);
        presenter.removeRequestLocationUpdate(this);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        presenter.loadWeatherWithLocationService(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
