
package br.com.luizalabs.luizalabs.weather.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiWeatherMain {

    @SerializedName("temp")
    private Double temp;

    @SerializedName("temp_min")
    private Double tempMin;

    @SerializedName("temp_max")
    private Double tempMax;

    public Double getTemp() {
        return temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }
}
