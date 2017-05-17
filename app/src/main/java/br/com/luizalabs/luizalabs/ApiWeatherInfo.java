
package br.com.luizalabs.luizalabs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiWeatherInfo {

    @SerializedName("id")
    public Integer id;

    @SerializedName("apiWeatherMain")
    public String main;

    @SerializedName("description")
    public String description;

    @SerializedName("icon")
    public String icon;

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
