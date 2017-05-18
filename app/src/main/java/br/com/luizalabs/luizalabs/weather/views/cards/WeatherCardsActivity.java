package br.com.luizalabs.luizalabs.weather.views.cards;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.List;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.App;
import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherCardsActivity extends AppCompatActivity implements WeatherCardsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    WeatherCardsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        DaggerWeatherCardsComponent.builder()
                .appComponent(App.getAppComponent())
                .weatherCardsModule(new WeatherCardsModule(this))
                .build()
                .inject(this);

        presenter.loadWeather();
        presenter.configureGoogleApiClient();
    }

    @Override
    protected void onStart() {
        presenter.connectGoogleApiClient();
        super.onStart();
    }

    @Override
    protected void onStop() {
        presenter.disconnectGoogleApiClient();
        super.onStop();
    }

    @Override
    public void configureWeatherCards(List<Weather> weathers) {
        WeatherCardsAdapter adapter = new WeatherCardsAdapter(weathers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void onGPSLocationFound(){
        Location lastPosition = presenter.getLastPosition();
        if(lastPosition != null){
            int distance = 50000;
            LatLng latLng = new LatLng(lastPosition.getLatitude(), lastPosition.getLongitude());
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            LatLngBounds initialBounds = builder.include(latLng).build();

            LatLng topRight = SphericalUtil.computeOffset(initialBounds.northeast, distance * Math.sqrt(2), 45);
            LatLng bottomRight = SphericalUtil.computeOffset(initialBounds.northeast, distance * Math.sqrt(2), 135);
            LatLng bottomLeft = SphericalUtil.computeOffset(initialBounds.southwest, distance * Math.sqrt(2), 225);
            LatLng topLeft = SphericalUtil.computeOffset(initialBounds.southwest, distance * Math.sqrt(2), 315);
        }
    }
}
