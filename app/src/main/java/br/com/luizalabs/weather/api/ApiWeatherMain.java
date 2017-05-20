
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

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
}
