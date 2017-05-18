
package br.com.luizalabs.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiWeathers {

    @SerializedName("list")
    private List<ApiWeatherCity> apiWeatherCities = new ArrayList<>();

    public List<ApiWeatherCity> getApiWeatherCities() {
        return apiWeatherCities;
    }
}
