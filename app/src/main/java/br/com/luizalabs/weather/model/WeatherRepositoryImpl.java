
package br.com.luizalabs.weather.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import br.com.luizalabs.utils.GoogleMapHelper;
import br.com.luizalabs.utils.NetworkHelper;
import br.com.luizalabs.weather.api.ApiWeathers;
import br.com.luizalabs.weather.api.WeatherApi;
import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String ZOOM_PARAM = ",1000";
    private static final String SHAREPREFERENCE_WEATHERS = "sharepreference_weathers";

    private WeatherApi weatherApi;

    private Gson gson;
    private Context appContext;
    private SharedPreferences preferences;

    public WeatherRepositoryImpl(Context appContext, Gson gson,
                                 WeatherApi weatherApi, SharedPreferences preferences) {
        this.weatherApi = weatherApi;
        this.preferences = preferences;
        this.gson = gson;
        this.appContext = appContext;
    }

    @Override
    public Observable<ApiWeathers> listByLocation(LatLng location) {
        if (NetworkHelper.isNetworkAvailable(appContext)){
            return listByLocationRemote(location).doOnNext(this::saveLocal);
        }else{
            return listByLocationLocal();
        }
    }

    private Observable<ApiWeathers> listByLocationRemote(LatLng location){
        String boundingBox = GoogleMapHelper.calculateBoundingBox(location, WeatherInteractor.FIFITY_KM) + ZOOM_PARAM;
        return weatherApi.listByArea(boundingBox);
    }

    private Observable<ApiWeathers> listByLocationLocal(){
        String json = preferences.getString(SHAREPREFERENCE_WEATHERS, "{\"list\":[{\"weather\":[{\"description\":\"description\",\"icon\":\"icon\"}],\"coord\":{\"Lon\":-34.8813,\"Lat\":-8.05428},\"main\":{\"temp\":1.0,\"temp_min\":1.0,\"temp_max\":1.0},\"name\":\"name\"}]}");
        if(StringUtils.isEmpty(json)){
            return Observable.empty();
        }else {
            return Observable.just(gson.fromJson(json, ApiWeathers.class));
        }
    }

    private void saveLocal(ApiWeathers weathers) {
        String json = gson.toJson(weathers);
        preferences.edit().putString(SHAREPREFERENCE_WEATHERS, json).apply();
    }
}
