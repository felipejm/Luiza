
package br.com.luizalabs.luizalabs.weather.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import br.com.luizalabs.luizalabs.weather.api.WeatherApi;
import io.reactivex.Observable;

public class WeatherInteractorImpl implements WeatherInteractor {

    private List<Weather> cache = new ArrayList<>();

    private WeatherApi weatherApi;
    private WeatherMapper mapper;

    public WeatherInteractorImpl(WeatherApi weatherApi, WeatherMapper mapper) {
        this.weatherApi = weatherApi;
        this.mapper = mapper;
    }

    @Override
    public Observable<List<Weather>> getWeather(){
       return weatherApi.getForArea("12,32,15,37,10")
               .map(apiWeathers -> mapper.transform(apiWeathers));
    }

    @Override
    public Observable<List<Weather>> getWeatherNearbyLocation(LatLng location){
        int distance = 50000;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLngBounds initialBounds = builder.include(location).build();

        LatLng topRight = SphericalUtil.computeOffset(initialBounds.northeast, distance * Math.sqrt(2), 45);
        LatLng bottomRight = SphericalUtil.computeOffset(initialBounds.northeast, distance * Math.sqrt(2), 135);
        LatLng bottomLeft = SphericalUtil.computeOffset(initialBounds.southwest, distance * Math.sqrt(2), 225);
        LatLng topLeft = SphericalUtil.computeOffset(initialBounds.southwest, distance * Math.sqrt(2), 315);

        return weatherApi.getForArea(String.format("%f,%f,%f,%f,7", topLeft.longitude, topLeft.latitude, bottomRight.longitude, bottomLeft.longitude))
                .map(apiWeathers -> mapper.transform(apiWeathers));
    }

    @Override
    public void setCache(List<Weather> weathers){
        this.cache = weathers;
    }

    @Override
    public List<Weather> getCache() {
        return cache;
    }
}
