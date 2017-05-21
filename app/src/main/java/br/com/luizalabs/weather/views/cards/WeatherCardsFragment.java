package br.com.luizalabs.weather.views.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import br.com.luizalabs.R;
import br.com.luizalabs.app.App;
import br.com.luizalabs.weather.model.Weather;
import br.com.luizalabs.weather.views.SwitchTemperatureUnitEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherCardsFragment extends Fragment implements WeatherCardsView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Inject
    WeatherCardsPresenter presenter;
    private WeatherCardsAdapter adapter;

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
        presenter.configureTemperatureUnit();
        if (adapter != null) {
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
