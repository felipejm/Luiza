
package br.com.luizalabs.weather.api;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiWeatherInfo that = (ApiWeatherInfo) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(main, that.main)
                .append(description, that.description)
                .append(icon, that.icon)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(main)
                .append(description)
                .append(icon)
                .toHashCode();
    }
}

