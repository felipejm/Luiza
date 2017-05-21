
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ApiWeatherCoord {

    @SerializedName("Lon")
    protected Double lon;

    @SerializedName("Lat")
    protected Double lat;

    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiWeatherCoord that = (ApiWeatherCoord) o;

        return new EqualsBuilder()
                .append(lon, that.lon)
                .append(lat, that.lat)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(lon)
                .append(lat)
                .toHashCode();
    }
}
