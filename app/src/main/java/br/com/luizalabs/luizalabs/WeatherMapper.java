
package br.com.luizalabs.luizalabs;

import java.util.List;

public class WeatherMapper {

    public Weather transform(ApiWeather apiWeather){
        Weather weather = new Weather();
        weather.setCityName(apiWeather.getName());

        List<ApiWeatherInfo> apiWeatherInfos = apiWeather.getApiWeatherInfo();
        if(!apiWeatherInfos.isEmpty()) {
            ApiWeatherInfo apiWeatherInfo = apiWeatherInfos.get(0);
            weather.setDescription(apiWeatherInfo.description);
        }

        ApiWeatherMain apiWeatherMain = apiWeather.getApiWeatherMain();
        if(apiWeatherMain != null){
            weather.setTemperature(apiWeatherMain.temp);
            weather.setTemperatureMax(apiWeatherMain.tempMax);
            weather.setTemperatureMin(apiWeatherMain.tempMin);
        }

        return weather;
    }
}
