
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

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
}
