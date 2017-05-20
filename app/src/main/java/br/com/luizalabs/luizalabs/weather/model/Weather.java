
package br.com.luizalabs.luizalabs.weather.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

import br.com.luizalabs.luizalabs.utils.TemperatureConverterHelper;

public class Weather implements Serializable {

    private Double latitude;
    private Double longitude;

    private String cityName;
    private String description;

    private String temperature;
    private String temperatureMin;
    private String temperatureMax;
    private TEMPERATURA_UNIT temperaturaUnit = TEMPERATURA_UNIT.CELSIUS;

    private String icon;

    public void changeTemperatureUnit(TEMPERATURA_UNIT temperaturaUnit) {
        if (this.temperaturaUnit == TEMPERATURA_UNIT.CELSIUS
                && temperaturaUnit == TEMPERATURA_UNIT.FAHRENHEIT) {

            this.temperaturaUnit = TEMPERATURA_UNIT.FAHRENHEIT;
            temperature = TemperatureConverterHelper.convertCelsiusToFahrenheit(temperature);
            temperatureMin = TemperatureConverterHelper.convertCelsiusToFahrenheit(temperatureMin);
            temperatureMax = TemperatureConverterHelper.convertCelsiusToFahrenheit(temperatureMax);

        } else if (this.temperaturaUnit == TEMPERATURA_UNIT.FAHRENHEIT
                && temperaturaUnit == TEMPERATURA_UNIT.CELSIUS) {

            this.temperaturaUnit = TEMPERATURA_UNIT.CELSIUS;
            temperature = TemperatureConverterHelper.convertFahrenheitToCelsius(temperature);
            temperatureMin = TemperatureConverterHelper.convertFahrenheitToCelsius(temperatureMin);
            temperatureMax = TemperatureConverterHelper.convertFahrenheitToCelsius(temperatureMax);
        }
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public TEMPERATURA_UNIT getTemperaturaUnit() {
        return temperaturaUnit;
    }

    public void setTemperaturaUnit(TEMPERATURA_UNIT temperaturaUnit) {
        this.temperaturaUnit = temperaturaUnit;
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
                .append(latitude, weather.latitude)
                .append(longitude, weather.longitude)
                .append(cityName, weather.cityName)
                .append(description, weather.description)
                .append(temperature, weather.temperature)
                .append(temperatureMin, weather.temperatureMin)
                .append(temperatureMax, weather.temperatureMax)
                .append(temperaturaUnit, weather.temperaturaUnit)
                .append(icon, weather.icon)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(latitude)
                .append(longitude)
                .append(cityName)
                .append(description)
                .append(temperature)
                .append(temperatureMin)
                .append(temperatureMax)
                .append(temperaturaUnit)
                .append(icon)
                .toHashCode();
    }
}
