
package br.com.luizalabs.luizalabs.weather.model;

import android.support.annotation.DrawableRes;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.utils.DrawableHelper;
import br.com.luizalabs.luizalabs.weather.api.ApiWeatherCity;
import br.com.luizalabs.luizalabs.weather.api.ApiWeatherCoord;
import br.com.luizalabs.luizalabs.weather.api.ApiWeatherInfo;
import br.com.luizalabs.luizalabs.weather.api.ApiWeatherMain;
import br.com.luizalabs.luizalabs.weather.api.ApiWeathers;

public class WeatherMapper {

    @Inject
    public WeatherMapper(){}

    public List<Weather> transform(ApiWeathers apiWeathres){
        List<Weather> weathers = new ArrayList<>();
        List<ApiWeatherCity> apiWeatherCities = apiWeathres.getApiWeatherCities();

        if(apiWeatherCities!= null) {
            for (ApiWeatherCity apiWeatherCity: apiWeatherCities) {
                weathers.add(transform(apiWeatherCity));
            }
        }

        return weathers;
    }

    private Weather transform(ApiWeatherCity apiWeatherCity){
        Weather weather = new Weather();
        weather.setCityName(StringUtils.capitalize(apiWeatherCity.getName()));

        ApiWeatherCoord apiWeatherCoord = apiWeatherCity.getApiWeatherCoord();
        if(apiWeatherCoord != null){
            weather.setLatitude(apiWeatherCoord.getLat());
            weather.setLongitude(apiWeatherCoord.getLon());
        }

        List<ApiWeatherInfo> apiWeatherInfos = apiWeatherCity.getApiWeatherInfo();
        if(!apiWeatherInfos.isEmpty()) {
            ApiWeatherInfo apiWeatherInfo = apiWeatherInfos.get(0);
            weather.setIcon(String.format("ic_%s", apiWeatherInfo.getIcon()));
            weather.setDescription(StringUtils.capitalize(apiWeatherInfo.getDescription()));
        }

        ApiWeatherMain apiWeatherMain = apiWeatherCity.getApiWeatherMain();
        if(apiWeatherMain != null){
            weather.setTemperature(String.valueOf(apiWeatherMain.getTemp() == null ? "" : String.format("%.0f", apiWeatherMain.getTemp())));
            weather.setTemperatureMax(String.valueOf(apiWeatherMain.getTempMax() == null ? "" :  String.format("%.0f", apiWeatherMain.getTempMax())));
            weather.setTemperatureMin(String.valueOf(apiWeatherMain.getTempMin() == null ? "" :  String.format("%.0f", apiWeatherMain.getTempMin())));
        }

        return weather;
    }

    private boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}
