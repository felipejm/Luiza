
package br.com.luizalabs.luizalabs.weather.api;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ApiWeather {

    @SerializedName("weather")
    private List<ApiWeatherInfo> apiWeatherInfo = new ArrayList<>();

    @SerializedName("base")
    private String base;

    @SerializedName("main")
    private ApiWeatherMain apiWeatherMain;

    @SerializedName("visibility")
    private Integer visibility;

    @SerializedName("dt")
    private Integer date;

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("cod")
    private Integer code;

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
