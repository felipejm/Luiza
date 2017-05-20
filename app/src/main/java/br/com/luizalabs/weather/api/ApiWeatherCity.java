
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiWeatherCity {

    @SerializedName("weather")
    protected List<ApiWeatherInfo> apiWeatherInfo = new ArrayList<>();

    @SerializedName("coord")
    protected ApiWeatherCoord apiWeatherCoord;

    @SerializedName("base")
    protected String base;

    @SerializedName("main")
    protected ApiWeatherMain apiWeatherMain;

    @SerializedName("visibility")
    protected Integer visibility;

    @SerializedName("dt")
    protected Integer date;

    @SerializedName("id")
    protected Integer id;

    @SerializedName("name")
    protected String name;

    @SerializedName("cod")
    protected Integer code;

    public ApiWeatherCoord getApiWeatherCoord() {
        return apiWeatherCoord;
    }

    public List<ApiWeatherInfo> getApiWeatherInfo() {
        return apiWeatherInfo;
    }

    public String getBase() {
        return base;
    }

    public ApiWeatherMain getApiWeatherMain() {
        return apiWeatherMain;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Integer getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}
