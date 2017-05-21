
package br.com.luizalabs.weather.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import br.com.luizalabs.utils.LocationHelper;
import io.reactivex.Observable;

public class WeatherInteractorImpl implements WeatherInteractor {

    private List<Weather> cache = new ArrayList<>();

    private WeatherMapper mapper;
    private WeatherRepository repository;

    public WeatherInteractorImpl(WeatherRepository repository,
                                 WeatherMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Observable<List<Weather>> listByLocation(LatLng location) {
        return repository.listByLocation(location)
                .map(apiWeathers -> mapper.transform(apiWeathers))
                .flatMapIterable(weathers -> weathers)
                .filter(weather -> {
                    LatLng latLng = new LatLng(weather.getLatitude(), weather.getLongitude());
                    return LocationHelper.distanceBetweenLessThen(location, latLng, FIFITY_KM);
                }).toList().toObservable();
    }

    @Override
    public Observable<List<Weather>> getCache() {
        return Observable.just(cache);
    }

    @Override
    public void setCache(List<Weather> weathers) {
        this.cache = weathers;
    }
}
