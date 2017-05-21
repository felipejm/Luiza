package br.com.luizalabs.weather.view.cards;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.luizalabs.ReflectionTestUtil;
import br.com.luizalabs.user.model.UserPreference;
import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.user.model.UserPreferenceTestUtil;
import br.com.luizalabs.weather.model.TemperatureUnitEnum;
import br.com.luizalabs.weather.model.Weather;
import br.com.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.weather.model.WeatherTestUtil;
import br.com.luizalabs.weather.views.cards.WeatherCardsPresenter;
import br.com.luizalabs.weather.views.cards.WeatherCardsPresenterImpl;
import br.com.luizalabs.weather.views.cards.WeatherCardsView;
import io.reactivex.Observable;

public class WeatherCardsPresenterTest {

    @Mock
    private UserPreferenceInteractor userPreferenceInteractor;

    @Mock
    private WeatherInteractor interactor;

    @Mock
    private WeatherCardsView view;

    private WeatherCardsPresenter presenter;

    private LatLng recifeLocation = new LatLng(-8.05428, -34.8813);
    private LatLng saoPauloLocation = new LatLng(-23.5505, -46.6333);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new WeatherCardsPresenterImpl(userPreferenceInteractor, interactor, view);
        presenter = Mockito.spy(presenter);
    }

    @Test
    public void loadWeathers(){
        UserPreference userPreference = UserPreferenceTestUtil.create(TemperatureUnitEnum.CELSIUS);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weather = WeatherTestUtil.create();
        List<Weather> weathers = new ArrayList<>(Arrays.asList(weather));

        Observable<List<Weather>> observable = Observable.just(weathers);
        Mockito.doReturn(observable).when(interactor).getCache();

        presenter.loadWeathers();

        List<Weather> weathersResult = ReflectionTestUtil.getPrivateField(presenter, "weathers");

        Assert.assertEquals(weathersResult.size(), 1);
        Assert.assertEquals(weathersResult.get(0), weather);
    }

    @Test
    public void loadWeathers_callView(){
        UserPreference userPreference = UserPreferenceTestUtil.create(-8.05428, -34.8813, TemperatureUnitEnum.CELSIUS);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weather = WeatherTestUtil.create();
        List<Weather> weathers = new ArrayList<>(Arrays.asList(weather));

        Observable<List<Weather>> observable = Observable.just(weathers);
        Mockito.doReturn(observable).when(interactor).getCache();

        presenter.loadWeathers();

        Mockito.verify(view).configureWeatherCards(Mockito.eq(weathers));
    }

    @Test
    public void loadWeathers_order(){
        UserPreference userPreference = UserPreferenceTestUtil.create(-8.05428, -34.8813, TemperatureUnitEnum.CELSIUS);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weatherRecife = WeatherTestUtil.create(-8.05428, -34.8813);
        Weather weatherSaoPaulo = WeatherTestUtil.create(-23.5505, -46.6333);

        List<Weather> weathers = new ArrayList<>(Arrays.asList(weatherSaoPaulo, weatherRecife));
        Observable<List<Weather>> observable = Observable.just(weathers);
        Mockito.doReturn(observable).when(interactor).getCache();

        presenter.loadWeathers();

        List<Weather> weathersResult = ReflectionTestUtil.getPrivateField(presenter, "weathers");

        Assert.assertEquals(weathersResult.size(), 2);
        Assert.assertEquals(weathersResult.get(0), weatherRecife);
        Assert.assertEquals(weathersResult.get(1), weatherSaoPaulo);
    }

    @Test
    public void loadWeathers_configureTemperatureUnit(){
        UserPreference userPreference = UserPreferenceTestUtil.create(-8.05428, -34.8813, TemperatureUnitEnum.FAHRENHEIT);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weather = WeatherTestUtil.create();
        List<Weather> weathers = new ArrayList<>(Arrays.asList(weather));

        Observable<List<Weather>> observable = Observable.just(weathers);
        Mockito.doReturn(observable).when(interactor).getCache();

        presenter.loadWeathers();
        Mockito.verify(presenter).configureTemperatureUnit();
    }

    @Test
    public void configureTemperatureUnit(){
        UserPreference userPreference = UserPreferenceTestUtil.create(TemperatureUnitEnum.FAHRENHEIT);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weather = WeatherTestUtil.create();

        List<Weather> weathers = new ArrayList<>();
        weathers.add(weather);

        ReflectionTestUtil.setPrivateField(presenter, "weathers", weathers);

        presenter.configureTemperatureUnit();

        Assert.assertEquals(weather.getTemperature(), "34");
        Assert.assertEquals(weather.getTemperatureMax(), "34");
        Assert.assertEquals(weather.getTemperatureMin(), "34");
    }
}
