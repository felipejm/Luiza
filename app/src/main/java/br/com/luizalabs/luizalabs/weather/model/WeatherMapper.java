
package br.com.luizalabs.luizalabs.weather.model;

import android.support.annotation.DrawableRes;

import java.util.List;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.AppScope;
import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.weather.api.ApiWeather;
import br.com.luizalabs.luizalabs.weather.api.ApiWeatherInfo;
import br.com.luizalabs.luizalabs.weather.api.ApiWeatherMain;

public class WeatherMapper {

    @Inject
    public WeatherMapper(){}

    public Weather transform(ApiWeather apiWeather){
        Weather weather = new Weather();
        weather.setCityName(apiWeather.getName());

        List<ApiWeatherInfo> apiWeatherInfos = apiWeather.getApiWeatherInfo();
        if(!apiWeatherInfos.isEmpty()) {
            ApiWeatherInfo apiWeatherInfo = apiWeatherInfos.get(0);

            weather.setWeatherIconRes(getWeatherIcon(apiWeatherInfo.getId()));
            weather.setDescription(apiWeatherInfo.getDescription());
        }

        ApiWeatherMain apiWeatherMain = apiWeather.getApiWeatherMain();
        if(apiWeatherMain != null){
            weather.setTemperature(String.valueOf(apiWeatherMain.getTemp() == null ? "" : String.format("%.0f", apiWeatherMain.getTemp())));
            weather.setTemperatureMax(String.valueOf(apiWeatherMain.getTempMax() == null ? "" :  String.format("%.0f", apiWeatherMain.getTempMax())));
            weather.setTemperatureMin(String.valueOf(apiWeatherMain.getTempMin() == null ? "" :  String.format("%.0f", apiWeatherMain.getTempMin())));
        }

        return weather;
    }

    @DrawableRes
    private int getWeatherIcon(int weatherId){
         if (isBetween(weatherId, 200, 299)) {
            return R.drawable.weather_storm;

        }else if (isBetween(weatherId, 300, 399)) {
            return R.drawable.weather_drizzle;

        }else if (isBetween(weatherId, 500, 599)) {
            return R.drawable.weather_rain;

        }else if (isBetween(weatherId, 600, 699)) {
            return R.drawable.weather_snow;

        }else if (isBetween(weatherId, 700, 799)) {
            return R.drawable.weather_mist;

        }else if (isBetween(weatherId, 800, 899)) {
            return R.drawable.weather_clear;

        }else {
             return R.drawable.weather_clear;
         }
    }

    private boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}
