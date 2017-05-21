package br.com.luizalabs.weather.view.maps;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.luizalabs.NetworkHelperTestUtil;
import br.com.luizalabs.ReflectionTestUtil;
import br.com.luizalabs.RxJavaAndroidTestUtil;
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
import io.reactivex.Observable;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GoogleMap.class, CameraPosition.class})
public class WeatherMapPresenterTest {

    @Mock
    private UserPreferenceInteractor userPreferenceInteractor;

    @Mock
    private WeatherInteractor interactor;

    @Mock
    private WeatherMapView view;

    @Mock
    private Context context;

    @Mock
    private CameraPosition cameraPosition;

    private GoogleMap googleMap;
    private WeatherMapPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        PowerMockito.mock(GoogleMap.class);
        googleMap = PowerMockito.mock(GoogleMap.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.doReturn(cameraPosition).when(googleMap).getCameraPosition();

        RxJavaAndroidTestUtil.prepareRxJavaAndroid();

        presenter = new WeatherMapPresenterImpl(userPreferenceInteractor, interactor, view);
        presenter = Mockito.spy(presenter);
    }

    @Test
    public void loadWeather_changeTemperatureUnit(){
        UserPreference userPreference = UserPreferenceTestUtil.create(TemperatureUnitEnum.FAHRENHEIT);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weather = WeatherTestUtil.create();
        List<Weather> weathers = new ArrayList<>(Arrays.asList(weather));

        Observable<List<Weather>> observable = Observable.just(weathers);
        Mockito.doReturn(observable).when(interactor).getCache();

        presenter.loadWeather(googleMap);

        List<Weather> weathersResult = ReflectionTestUtil.getPrivateField(presenter, "weathers");

        Assert.assertEquals(weathersResult.get(0).getTemperatureMin(), "34");
        Assert.assertEquals(weathersResult.get(0).getTemperatureMax(), "34");
        Assert.assertEquals(weathersResult.get(0).getTemperature(), "34");
    }

    @Test
    public void loadWeather(){
        UserPreference userPreference = UserPreferenceTestUtil.create();
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weather = WeatherTestUtil.create();
        List<Weather> weathers = new ArrayList<>(Arrays.asList(weather));

        Observable<List<Weather>> observable = Observable.just(weathers);
        Mockito.doReturn(observable).when(interactor).getCache();

        presenter.loadWeather(googleMap);

        List<Weather> weathersResult = ReflectionTestUtil.getPrivateField(presenter, "weathers");

        Assert.assertEquals(weathers, weathersResult);
        Mockito.verify(view).createCityMarker(Mockito.eq(googleMap), Mockito.eq(weather));
    }

    @Test
    public void loadWeatherFromLocation_changeTemperatureUnit(){
        UserPreference userPreference = UserPreferenceTestUtil.create(TemperatureUnitEnum.FAHRENHEIT);
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weather = WeatherTestUtil.create();
        List<Weather> weathers = new ArrayList<>(Arrays.asList(weather));

        Observable<List<Weather>> observable = Observable.just(weathers);

        LatLng location = new LatLng(-8.05428, -34.8813);
        Mockito.doReturn(observable).when(interactor).listByLocation(location);

        presenter.loadWeatherFromLocation(googleMap, location);

        List<Weather> weathersResult = ReflectionTestUtil.getPrivateField(presenter, "weathers");

        Assert.assertEquals(weathersResult.get(0).getTemperatureMin(), "34");
        Assert.assertEquals(weathersResult.get(0).getTemperatureMax(), "34");
        Assert.assertEquals(weathersResult.get(0).getTemperature(), "34");
    }

    @Test
    public void loadWeatherFromLocation(){
        UserPreference userPreference = UserPreferenceTestUtil.create();
        Mockito.doReturn(userPreference).when(userPreferenceInteractor).get();

        Weather weather = WeatherTestUtil.create();
        List<Weather> weathers = new ArrayList<>(Arrays.asList(weather));

        Observable<List<Weather>> observable = Observable.just(weathers);

        LatLng location = new LatLng(-8.05428, -34.8813);
        Mockito.doReturn(observable).when(interactor).listByLocation(location);

        presenter.loadWeatherFromLocation(googleMap, location);

        List<Weather> weathersResult = ReflectionTestUtil.getPrivateField(presenter, "weathers");

        Assert.assertEquals(weathers, weathersResult);
        Mockito.verify(interactor).setCache(Mockito.eq(weathers));
        Mockito.verify(view).createCityMarker(Mockito.eq(googleMap), Mockito.eq(weather));
    }

    @Test
    public void onCameraChangePosition_offline(){
        NetworkHelperTestUtil.mockNetworkAvailability(context, false);

        LatLng location = new LatLng(-8.05428, -34.8813);
        ReflectionTestUtil.setPrivateField(cameraPosition, "target", location);

        Mockito.doNothing().when(presenter).saveLastLocation(location);
        Mockito.doNothing().when(presenter).loadWeatherFromLocation(googleMap, location);

        presenter.onCameraChangePosition(googleMap, context);

        Mockito.verify(view, Mockito.never()).removeAllCityMarkers();
        Mockito.verify(view, Mockito.never()).drawCircleInCenter(Mockito.eq(googleMap), Mockito.eq(location));
    }

    @Test
    public void onCameraChangePosition(){
        NetworkHelperTestUtil.mockNetworkAvailability(context, true);

        LatLng location = new LatLng(-8.05428, -34.8813);
        ReflectionTestUtil.setPrivateField(cameraPosition, "target", location);

        Mockito.doNothing().when(presenter).saveLastLocation(location);
        Mockito.doNothing().when(presenter).loadWeatherFromLocation(googleMap, location);

        presenter.onCameraChangePosition(googleMap, context);

        Mockito.verify(view).removeAllCityMarkers();
        Mockito.verify(view).drawCircleInCenter(Mockito.eq(googleMap), Mockito.eq(location));
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
