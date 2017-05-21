package br.com.luizalabs.weather.view.maps;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.luizalabs.ReflectionTestUtil;
import br.com.luizalabs.user.model.UserPreference;
import br.com.luizalabs.user.model.UserPreferenceInteractor;
import br.com.luizalabs.user.model.UserPreferenceTestUtil;
import br.com.luizalabs.weather.model.TemperatureUnitEnum;
import br.com.luizalabs.weather.model.Weather;
import br.com.luizalabs.weather.model.WeatherInteractor;
import br.com.luizalabs.weather.model.WeatherTestUtil;
import br.com.luizalabs.weather.views.map.WeatherMapPresenter;
import br.com.luizalabs.weather.views.map.WeatherMapPresenterImpl;
import br.com.luizalabs.weather.views.map.WeatherMapView;

public class WeatherMapPresenterTest {

    @Mock
    private UserPreferenceInteractor userPreferenceInteractor;

    @Mock
    private WeatherInteractor interactor;

    @Mock
    private WeatherMapView view;

    private WeatherMapPresenter presenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        presenter = new WeatherMapPresenterImpl(userPreferenceInteractor, interactor, view);
        presenter = Mockito.spy(presenter);
    }

    @Test
    public void saveLastLocation(){
        LatLng location = new LatLng(-8.05428, -34.8813);
        presenter.saveLastLocation(location);
        Mockito.verify(userPreferenceInteractor).saveLastLocation(-8.05428, -34.8813);
    }

    @Test
    public void switchTemperatureUnit_weatherNull(){
        UserPreference userPreference = UserPreferenceTestUtil.create(TemperatureUnitEnum.FAHRENHEIT);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        ReflectionTestUtil.setPrivateField(presenter, "weathers", null);

        presenter.switchTemperatureUnit();

        Mockito.verify(view, Mockito.never()).updateMarkersInfoWindow();
    }

    @Test
    public void switchTemperatureUnit_updateMarkersInfoWindow(){
        UserPreference userPreference = UserPreferenceTestUtil.create(TemperatureUnitEnum.FAHRENHEIT);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        List<Weather> weathers = new ArrayList<>();
        weathers.add(WeatherTestUtil.create());

        ReflectionTestUtil.setPrivateField(presenter, "weathers", weathers);

        presenter.switchTemperatureUnit();
        Mockito.verify(view).updateMarkersInfoWindow();
    }

    @Test
    public void switchTemperatureUnit(){
        UserPreference userPreference = UserPreferenceTestUtil.create(TemperatureUnitEnum.FAHRENHEIT);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        List<Weather> weathers = new ArrayList<>();
        weathers.add(WeatherTestUtil.create());

        ReflectionTestUtil.setPrivateField(presenter, "weathers", weathers);

        presenter.switchTemperatureUnit();

        Assert.assertEquals(weathers.get(0).getTemperature(),"34");
        Assert.assertEquals(weathers.get(0).getTemperatureMin(),"34");
        Assert.assertEquals(weathers.get(0).getTemperatureMin(),"34");
    }

    @Test
    public void moveMapToLastLocation(){
        UserPreference userPreference = UserPreferenceTestUtil.create();
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        presenter.moveMapToLastLocation();

        LatLng location = new LatLng(userPreference.getLastLocationLatitude(), userPreference.getLastLocationLongitude());
        Mockito.verify(view).moveMapToMyLocation(Mockito.eq(location));
    }

}
