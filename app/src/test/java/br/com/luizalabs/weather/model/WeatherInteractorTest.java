package br.com.luizalabs.weather.model;

import com.google.android.gms.maps.model.LatLng;

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
import java.util.List;

import br.com.luizalabs.utils.LocationHelper;
import br.com.luizalabs.weather.api.ApiWeatherCity;
import br.com.luizalabs.weather.api.ApiWeatherTestUtil;
import br.com.luizalabs.weather.api.ApiWeathers;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;


@RunWith(PowerMockRunner.class)
@PrepareForTest(LocationHelper.class)
public class WeatherInteractorTest {

    @Mock
    private WeatherRepository repository;
    private WeatherMapper mapper = new WeatherMapper();
    private WeatherInteractor interactor;

    private LatLng recifeLocation = new LatLng(-8.05428, -34.8813);
    private LatLng saoPauloLocation = new LatLng(-23.5505, -46.6333);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new WeatherInteractorImpl(repository, mapper);
        PowerMockito.mockStatic(LocationHelper.class);
    }

    @Test
    public void getWeatherOfLocation() {
        List<ApiWeatherCity> apiWeatherCities = new ArrayList<>();
        apiWeatherCities.add(ApiWeatherTestUtil.createApiCitiy(recifeLocation));
        apiWeatherCities.add(ApiWeatherTestUtil.createApiCitiy(saoPauloLocation));

        Observable<ApiWeathers> observable = Observable.just(ApiWeatherTestUtil.create(apiWeatherCities));

        Mockito.when(repository.listByLocation(Mockito.any())).thenReturn(observable);
        Mockito.when(LocationHelper.distanceBetweenLessThen(recifeLocation, recifeLocation, 50000)).thenReturn(true);
        Mockito.when(LocationHelper.distanceBetweenLessThen(recifeLocation, saoPauloLocation, 50000)).thenReturn(false);

        TestObserver<List<Weather>> testSubscriber = new TestObserver<>();
        interactor.listByLocation(recifeLocation).subscribe(testSubscriber);

        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(WeatherTestUtil.create(-8.05428, -34.8813));

        testSubscriber.assertResult(weathers);
        testSubscriber.onComplete();
    }
}