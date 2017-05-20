package br.com.luizalabs.luizalabs.weather.views.cards;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return new WeatherCardsFragment();
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
        return view;
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
}
