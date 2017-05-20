package br.com.luizalabs.weather.api;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class ApiWeatherTestUtil {

    public static ApiWeathers create() {
        ApiWeatherCity apiWeatherCity = createApiCitiy(new LatLng(-8.05428, -34.8813));

        List<ApiWeatherCity> apiWeatherCities = new ArrayList<>();
        apiWeatherCities.add(apiWeatherCity);

        ApiWeathers apiWeathers = new ApiWeathers();
        apiWeathers.apiWeatherCities = apiWeatherCities;

        return apiWeathers;
    }

    public static ApiWeathers create(List<ApiWeatherCity> apiWeatherCities) {
        ApiWeathers apiWeathers = new ApiWeathers();
        apiWeathers.apiWeatherCities = apiWeatherCities;

        return apiWeathers;
    }

    public static ApiWeatherCity createApiCitiy(LatLng location) {
        ApiWeatherInfo apiWeatherInfo = new ApiWeatherInfo();
        apiWeatherInfo.description = "description";
        apiWeatherInfo.icon = "icon";

        List<ApiWeatherInfo> apiWeatherInfos = new ArrayList<>();
        apiWeatherInfos.add(apiWeatherInfo);

        ApiWeatherMain apiWeatherMain = new ApiWeatherMain();
        apiWeatherMain.temp = 1.0;
        apiWeatherMain.tempMax = 1.0;
        apiWeatherMain.tempMin = 1.0;

        ApiWeatherCoord apiWeatherCoord = new ApiWeatherCoord();
        apiWeatherCoord.lat = location.latitude;
        apiWeatherCoord.lon = location.longitude;

        ApiWeatherCity apiWeatherCity = new ApiWeatherCity();
        apiWeatherCity.apiWeatherCoord = apiWeatherCoord;
        apiWeatherCity.apiWeatherMain = apiWeatherMain;
        apiWeatherCity.name = "name";
        apiWeatherCity.apiWeatherInfo = apiWeatherInfos;
        return apiWeatherCity;
    }
}
