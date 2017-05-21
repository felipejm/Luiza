
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiWeatherCity that = (ApiWeatherCity) o;

        return new EqualsBuilder()
                .append(apiWeatherInfo, that.apiWeatherInfo)
                .append(apiWeatherCoord, that.apiWeatherCoord)
                .append(base, that.base)
                .append(apiWeatherMain, that.apiWeatherMain)
                .append(visibility, that.visibility)
                .append(date, that.date)
                .append(id, that.id)
                .append(name, that.name)
                .append(code, that.code)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(apiWeatherInfo)
                .append(apiWeatherCoord)
                .append(base)
                .append(apiWeatherMain)
                .append(visibility)
                .append(date)
                .append(id)
                .append(name)
                .append(code)
                .toHashCode();
    }
}
