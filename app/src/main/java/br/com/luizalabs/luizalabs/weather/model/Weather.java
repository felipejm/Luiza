
package br.com.luizalabs.luizalabs.weather.model;

import android.support.annotation.DrawableRes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class Weather implements Serializable{

    private String cityName;
    private String description;

    private String temperature;
    private String temperatureMin;
    private String temperatureMax;

    @DrawableRes
    private int weatherIconRes;

    @DrawableRes
    public int getWeatherIconRes() {
        return weatherIconRes;
    }

    public void setWeatherIconRes(@DrawableRes int weatherIconRes) {
        this.weatherIconRes = weatherIconRes;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(String temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        return new EqualsBuilder()
                .append(temperature, weather.temperature)
                .append(temperatureMin, weather.temperatureMin)
                .append(temperatureMax, weather.temperatureMax)
                .append(cityName, weather.cityName)
                .append(description, weather.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(cityName)
                .append(description)
                .append(temperature)
                .append(temperatureMin)
                .append(temperatureMax)
                .toHashCode();
    }
}
