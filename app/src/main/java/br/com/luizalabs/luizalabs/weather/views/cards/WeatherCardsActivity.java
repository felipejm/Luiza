package br.com.luizalabs.luizalabs.weather.views.cards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.App;
import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.weather.model.Weather;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherCardsActivity extends AppCompatActivity  implements WeatherCardsView {

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
    }

    @Override
    public void configureWeatherCards(List<Weather> weathers) {
        WeatherCardsAdapter adapter = new WeatherCardsAdapter(weathers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
