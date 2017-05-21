
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class ApiWeathers {

    @SerializedName("list")
    protected List<ApiWeatherCity> apiWeatherCities = new ArrayList<>();

    public List<ApiWeatherCity> getApiWeatherCities() {
        return apiWeatherCities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiWeathers that = (ApiWeathers) o;

        return new EqualsBuilder()
                .append(apiWeatherCities, that.apiWeatherCities)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(apiWeatherCities)
                .toHashCode();
    }
}
