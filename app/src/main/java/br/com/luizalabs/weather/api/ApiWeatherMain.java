
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ApiWeatherMain {

    @SerializedName("temp")
    protected Double temp;

    @SerializedName("temp_min")
    protected Double tempMin;

    @SerializedName("temp_max")
    protected Double tempMax;

    public Double getTemp() {
        return temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiWeatherMain that = (ApiWeatherMain) o;

        return new EqualsBuilder()
                .append(temp, that.temp)
                .append(tempMin, that.tempMin)
                .append(tempMax, that.tempMax)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(temp)
                .append(tempMin)
                .append(tempMax)
                .toHashCode();
    }
}
