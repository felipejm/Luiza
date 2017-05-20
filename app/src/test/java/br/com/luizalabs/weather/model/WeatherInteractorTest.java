package br.com.luizalabs.weather.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

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
import br.com.luizalabs.utils.LocationHelper;
import br.com.luizalabs.weather.api.ApiWeatherCity;
import br.com.luizalabs.weather.api.ApiWeatherTestUtil;
import br.com.luizalabs.weather.api.ApiWeathers;
import br.com.luizalabs.weather.api.WeatherApi;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;


@RunWith(PowerMockRunner.class)
@PrepareForTest(LocationHelper.class)
public class WeatherInteractorTest {

    @Mock
    SharedPreferences.Editor editor;
    @Mock
    private WeatherApi weatherApi;
    @Mock
    private SharedPreferences preferences;
    @Mock
    private Context appContext;
    private Gson gson = new Gson();
    private WeatherMapper mapper = new WeatherMapper();
    private WeatherInteractor interactor;

    private String weatherJsonMock = "[{\"latitude\":-8.05428,\"longitude\":-34.8813,\"cityName\":\"Name\",\"description\":\"Description\",\"temperature\":\"1\",\"temperatureMin\":\"1\",\"temperatureMax\":\"1\",\"temperaturaUnit\":\"CELSIUS\",\"icon\":\"ic_icon\"}]";
    private LatLng recifeLocation = new LatLng(-8.05428, -34.8813);
    private LatLng saoPauloLocation = new LatLng(-23.5505, -46.6333);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new WeatherInteractorImpl(appContext, gson,
                preferences, weatherApi, mapper);

        Mockito.doReturn(editor).when(preferences).edit();
        PowerMockito.mockStatic(LocationHelper.class);
    }

    @Test
    public void getWeatherOfLocation_loadWeatherRemote_filterByDistance() {
        List<ApiWeatherCity> apiWeatherCities = new ArrayList<>();
        apiWeatherCities.add(ApiWeatherTestUtil.createApiCitiy(recifeLocation));
        apiWeatherCities.add(ApiWeatherTestUtil.createApiCitiy(saoPauloLocation));

        Observable<ApiWeathers> observable = Observable.just(ApiWeatherTestUtil.create(apiWeatherCities));

        NetworkHelperTestUtil.mockNetworkAvailability(appContext, true);
        Mockito.when(editor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(editor);
        Mockito.when(weatherApi.getForArea(Mockito.anyString())).thenReturn(observable);
        Mockito.when(LocationHelper.distanceBetweenLessThen(recifeLocation, recifeLocation, 50000)).thenReturn(true);
        Mockito.when(LocationHelper.distanceBetweenLessThen(recifeLocation, saoPauloLocation, 50000)).thenReturn(false);

        TestObserver<List<Weather>> testSubscriber = new TestObserver<>();
        interactor.getFromLocation(recifeLocation).subscribe(testSubscriber);

        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(WeatherTestUtil.create(-8.05428, -34.8813));

        testSubscriber.assertResult(weathers);
        testSubscriber.onComplete();
    }

    @Test
    public void getWeatherOfLocation_loadWeatherRemote() {
        ApiWeathers apiWeathers = ApiWeatherTestUtil.create();
        Observable<ApiWeathers> observable = Observable.just(apiWeathers);

        NetworkHelperTestUtil.mockNetworkAvailability(appContext, true);
        Mockito.when(LocationHelper.distanceBetweenLessThen(recifeLocation, new LatLng(-8.05428, -34.8813), 50000)).thenReturn(true);
        Mockito.when(editor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(editor);
        Mockito.when(weatherApi.getForArea(Mockito.anyString())).thenReturn(observable);

        TestObserver<List<Weather>> testSubscriber = new TestObserver<>();
        interactor.getFromLocation(recifeLocation).subscribe(testSubscriber);

        testSubscriber.assertResult(new ArrayList<>(Arrays.asList(WeatherTestUtil.create())));
        testSubscriber.onComplete();
    }

    @Test
    public void getWeatherOfLocation_loadWeatherLocal_empty() {
        NetworkHelperTestUtil.mockNetworkAvailability(appContext, false);
        Mockito.when(preferences.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("[]");

        TestObserver<List<Weather>> testSubscriber = new TestObserver<>();
        interactor.getFromLocation(recifeLocation).subscribe(testSubscriber);

        testSubscriber.assertResult(new ArrayList<>());
        testSubscriber.onComplete();
    }

    @Test
    public void getWeatherOfLocation_loadWeatherLocal() {
        NetworkHelperTestUtil.mockNetworkAvailability(appContext, false);
        Mockito.when(preferences.getString(Mockito.anyString(), Mockito.anyString())).thenReturn(weatherJsonMock);
        Weather weather = WeatherTestUtil.create();

        TestObserver<List<Weather>> testSubscriber = new TestObserver<>();
        interactor.getFromLocation(recifeLocation).subscribe(testSubscriber);

        testSubscriber.assertResult(new ArrayList<>(Arrays.asList(weather)));
        testSubscriber.onComplete();
    }
}