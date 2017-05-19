package br.com.luizalabs.luizalabs.weather.views.cards;

import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherCardsFragment extends Fragment implements WeatherCardsView {

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

        presenter.loadWeathers();
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

    @Subscribe(sticky = true)
    public void onSwitchTemperatureUnit(SwitchTemperatureUnitEvent event){
        presenter.configureTemperatureUnit();
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void configureWeatherCards(List<Weather> weathers) {
        adapter = new WeatherCardsAdapter(weathers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

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
