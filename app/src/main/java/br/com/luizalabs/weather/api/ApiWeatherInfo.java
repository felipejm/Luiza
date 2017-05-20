
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

public class ApiWeatherInfo {

    @SerializedName("id")
    protected Integer id;

    @SerializedName("main")
    protected String main;

    @SerializedName("description")
    protected String description;

    @SerializedName("icon")
    protected String icon;

    public Integer getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}