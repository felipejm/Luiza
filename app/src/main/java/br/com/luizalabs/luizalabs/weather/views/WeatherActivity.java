package br.com.luizalabs.luizalabs.weather.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.App;
import br.com.luizalabs.luizalabs.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements WeatherView{

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

        presenter.loadWeathers();
    }

    @Override
    public void onWeatherLoaded(){
        invalidateOptionsMenu();
    }

    @Override
    public void updateFragment(Fragment fragment, boolean fadeAnimation) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if(fadeAnimation) {
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);;
        }
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(presenter.isNotShowingLoadingFragment()) {
            getMenuInflater().inflate(R.menu.menu, menu);
            presenter.configureToolbarTemperatureUnitIcon(menu.findItem(R.id.action_unit));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
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
}
