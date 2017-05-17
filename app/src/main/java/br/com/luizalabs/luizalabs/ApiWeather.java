
package br.com.luizalabs.luizalabs;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ApiWeather {

    @SerializedName("apiWeatherInfo")
    public List<ApiWeatherInfo> apiWeatherInfo = new ArrayList<>();

    @SerializedName("base")
    public String base;

    @SerializedName("apiWeatherMain")
    public ApiWeatherMain apiWeatherMain;

    @SerializedName("visibility")
    public Integer visibility;

    @SerializedName("dt")
    public Integer date;

    @SerializedName("id")
    public Integer id;

    @SerializedName("name")
    public String name;

    @SerializedName("cod")
    public Integer code;

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
