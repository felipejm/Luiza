package br.com.luizalabs.luizalabs.weather.views;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.SupportMapFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.user.model.UserPreference;
import br.com.luizalabs.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.luizalabs.utils.RxComposer;
import br.com.luizalabs.luizalabs.weather.model.TEMPERATURA_UNIT;
import br.com.luizalabs.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsFragment;
import br.com.luizalabs.luizalabs.weather.views.map.WeatherMapFragment;

public class WeatherPresenterImpl implements WeatherPresenter{

    private enum WEATHER_FRAGMENT{
        LOADING, CARDS, MAP
    }

    private UserPreferenceInteractor userPreferenceInteractor;
    private WeatherInteractor interactor;
    private WeatherView view;

    private WEATHER_FRAGMENT currentFragment;

    public WeatherPresenterImpl(UserPreferenceInteractor userPreferenceInteractor,
                                WeatherInteractor interactor, WeatherView view) {
        this.userPreferenceInteractor = userPreferenceInteractor;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void loadWeathers(){
        if(interactor.getCache() == null || interactor.getCache().isEmpty()) {
            interactor.getWeather()
                    .compose(RxComposer.newThread())
                    .doOnSubscribe(disposable -> showLoadingFragment())
                    .doOnError(throwable -> Log.e("WeatherPresenter", "loadWeathers", throwable))
                    .subscribe(weathers -> {
                        interactor.setCache(weathers);
                        view.onWeatherLoaded();
                        showCardsFragment();
                    });
        }else{
            showCardsFragment();
        }
    }

    @Override
    public void showLoadingFragment() {
        currentFragment = WEATHER_FRAGMENT.LOADING;
        view.updateFragment(LoadingFragment.newInstance(), false);
    }

    @Override
    public void showCardsFragment() {
        currentFragment = WEATHER_FRAGMENT.CARDS;
        view.updateFragment(WeatherCardsFragment.newInstance(), true);
    }

    @Override
    public void switchFragments() {
        if(currentFragment == WEATHER_FRAGMENT.MAP) {
            currentFragment = WEATHER_FRAGMENT.CARDS;
            view.updateFragment(WeatherCardsFragment.newInstance(), true);
        }else{
            currentFragment = WEATHER_FRAGMENT.MAP;
            view.updateFragment(WeatherMapFragment.newInstance(), true);
        }
    }

    @Override
    public boolean isNotShowingLoadingFragment() {
        return currentFragment != WEATHER_FRAGMENT.LOADING;
    }

    @Override
    public void switchToolbarTemperatureUnitIcon(MenuItem item){
        UserPreference userPreference = userPreferenceInteractor.get();

        if(userPreference.getTemperaturaUnit() == TEMPERATURA_UNIT.CELSIUS){
            item.setIcon(R.drawable.ic_fahrenheit);
            item.setTitle(R.string.change_to_celsius);
            userPreference.setTemperaturaUnit(TEMPERATURA_UNIT.FAHRENHEIT);
        }else{
            item.setIcon(R.drawable.ic_celsius);
            item.setTitle(R.string.change_to_fahrenheit);
            userPreference.setTemperaturaUnit(TEMPERATURA_UNIT.CELSIUS);
        }

        userPreferenceInteractor.save(userPreference);
    }

    @Override
    public void switchToolbarMapListIcon(MenuItem item){
        if(currentFragment == WEATHER_FRAGMENT.CARDS){
            item.setIcon(R.drawable.ic_list);
            item.setTitle(R.string.change_to_list);

        }else{
            item.setIcon(R.drawable.ic_maps);
            item.setTitle(R.string.change_to_map);

        }
    }

    @Override
    public void configureToolbarTemperatureUnitIcon(MenuItem item){
        UserPreference userPreference = userPreferenceInteractor.get();

        if(userPreference.getTemperaturaUnit() == TEMPERATURA_UNIT.CELSIUS){
            item.setIcon(R.drawable.ic_celsius);
            item.setTitle(R.string.change_to_fahrenheit);
        }else{
            item.setIcon(R.drawable.ic_fahrenheit);
            item.setTitle(R.string.change_to_celsius);
        }
    }
}
