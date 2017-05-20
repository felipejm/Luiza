
package br.com.luizalabs.weather.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.luizalabs.utils.GoogleMapHelper;
import br.com.luizalabs.utils.LocationHelper;
import br.com.luizalabs.utils.NetworkHelper;
import br.com.luizalabs.weather.api.WeatherApi;
import io.reactivex.Observable;

public class WeatherInteractorImpl implements WeatherInteractor {

    private static final String ZOOM_PARAM = ",1000";
    private static final String SHAREPREFERENCE_WEATHERS = "sharepreference_weathers";

    private List<Weather> cache = new ArrayList<>();

    private WeatherApi weatherApi;
    private WeatherMapper mapper;

    private Gson gson;
    private SharedPreferences preferences;

    private Context appContext;

    public WeatherInteractorImpl(Context appContext, Gson gson,
                                 SharedPreferences preferences,
                                 WeatherApi weatherApi, WeatherMapper mapper) {
        this.weatherApi = weatherApi;
        this.mapper = mapper;
        this.appContext = appContext;
        this.preferences = preferences;
        this.gson = gson;
    }

    @Override
    public Observable<List<Weather>> getFromLocation(LatLng location) {
        Observable<List<Weather>> observable;
        if (NetworkHelper.isNetworkAvailable(appContext)) {
            return getRemote(location)
                    .doOnNext(this::saveLocal);
        } else {
            observable = getLocal();
        }

        return observable;
    }

    private Observable<List<Weather>> getRemote(LatLng location) {
        String boundingBox = GoogleMapHelper.calculateBoundingBox(location, FIFITY_KM) + ZOOM_PARAM;
        return weatherApi.getForArea(boundingBox)
                .map(apiWeathers -> mapper.transform(apiWeathers))
                .flatMapIterable(weathers -> weathers)
                .filter(weather -> {
                    LatLng latLng = new LatLng(weather.getLatitude(), weather.getLongitude());
                    return LocationHelper.distanceBetweenLessThen(location, latLng, FIFITY_KM);
                }).toList().toObservable();
    }

    private Observable<List<Weather>> getLocal() {
        String json = preferences.getString(SHAREPREFERENCE_WEATHERS, "[]");
        Type listType = new TypeToken<ArrayList<Weather>>() {
        }.getType();
        return Observable.just(gson.fromJson(json, listType));
    }

    private void saveLocal(List<Weather> weathers) {
        String json = gson.toJson(weathers);
        preferences.edit().putString(SHAREPREFERENCE_WEATHERS, json).apply();
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
