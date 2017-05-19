package br.com.luizalabs.luizalabs.weather.views.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Inject
    WeatherMapPresenter presenter;

    public static WeatherMapFragment newInstance() {
        WeatherMapFragment fragment = new WeatherMapFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DaggerWeatherMapComponent.builder()
                .appComponent(App.getAppComponent())
                .weatherMapModule(new WeatherMapModule(this))
                .build()
                .inject(this);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onSwitchTemperatureUnit(SwitchTemperatureUnitEvent event){
    }

}
