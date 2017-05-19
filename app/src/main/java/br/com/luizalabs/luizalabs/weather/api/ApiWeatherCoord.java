
package br.com.luizalabs.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

public class ApiWeatherCoord {

    @SerializedName("Lon")
    private Double lon;

    @SerializedName("Lat")
    private Double lat;

    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }
}
