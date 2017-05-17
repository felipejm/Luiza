
package br.com.luizalabs.luizalabs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiWeatherMain {

    @SerializedName("temp")
    @Expose
    public Double temp;
    @SerializedName("pressure")
    @Expose
    public Integer pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("temp_min")
    @Expose
    public Double tempMin;
    @SerializedName("temp_max")
    @Expose
    public Double tempMax;

}
