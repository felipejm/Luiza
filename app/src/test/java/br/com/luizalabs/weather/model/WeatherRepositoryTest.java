package br.com.luizalabs.weather.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.luizalabs.NetworkHelperTestUtil;
import br.com.luizalabs.weather.api.ApiWeatherTestUtil;
import br.com.luizalabs.weather.api.ApiWeathers;
import br.com.luizalabs.weather.api.WeatherApi;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;


public class WeatherRepositoryTest {

    @Mock
    SharedPreferences.Editor editor;

    @Mock
    private WeatherApi weatherApi;

    @Mock
    private SharedPreferences preferences;

    @Mock
    private Context appContext;

    private Gson gson = new Gson();
    private WeatherRepository repository;

    private String apiWeatherJsonMock = "{\"list\":[{\"weather\":[{\"description\":\"description\",\"icon\":\"icon\"}],\"coord\":{\"Lon\":-34.8813,\"Lat\":-8.05428},\"main\":{\"temp\":1.0,\"temp_min\":1.0,\"temp_max\":1.0},\"name\":\"name\"}]}";
    private LatLng recifeLocation = new LatLng(-8.05428, -34.8813);
    private LatLng saoPauloLocation = new LatLng(-23.5505, -46.6333);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new WeatherRepositoryImpl(appContext, gson,
                weatherApi, preferences);

        Mockito.doReturn(editor).when(preferences).edit();
    }

    @Test
    public void getWeatherOfLocation_loadWeatherRemote() {
        ApiWeathers apiWeathers = ApiWeatherTestUtil.create();
        Observable<ApiWeathers> observable = Observable.just(apiWeathers);

        NetworkHelperTestUtil.mockNetworkAvailability(appContext, true);
        Mockito.when(editor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(editor);
        Mockito.when(weatherApi.listByArea(Mockito.anyString())).thenReturn(observable);

        TestObserver<ApiWeathers> testSubscriber = new TestObserver<>();
        repository.listByLocation(recifeLocation).subscribe(testSubscriber);

        testSubscriber.assertResult(ApiWeatherTestUtil.create());
        testSubscriber.onComplete();

        Mockito.verify(editor).putString("sharepreference_weathers", apiWeatherJsonMock);
    }

    @Test
    public void getWeatherOfLocation_loadWeatherLocal_empty() {
        NetworkHelperTestUtil.mockNetworkAvailability(appContext, false);
        Mockito.when(preferences.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("");

        TestObserver<ApiWeathers> testSubscriber = new TestObserver<>();
        repository.listByLocation(recifeLocation).subscribe(testSubscriber);

        testSubscriber.assertComplete();
        testSubscriber.assertValueCount(0);
    }

    @Test
    public void getWeatherOfLocation_loadWeatherLocal() {
        NetworkHelperTestUtil.mockNetworkAvailability(appContext, false);
        ApiWeathers apiWeathers = ApiWeatherTestUtil.create();

        Mockito.when(preferences.getString(Mockito.anyString(), Mockito.anyString())).thenReturn(apiWeatherJsonMock);

        TestObserver<ApiWeathers> testSubscriber = new TestObserver<>();
        repository.listByLocation(recifeLocation).subscribe(testSubscriber);

        testSubscriber.assertResult(apiWeathers);
        testSubscriber.onComplete();
    }
}