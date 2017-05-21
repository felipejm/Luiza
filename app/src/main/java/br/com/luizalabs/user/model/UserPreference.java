package br.com.luizalabs.user.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

import br.com.luizalabs.weather.model.TemperatureUnitEnum;

public class UserPreference implements Serializable {

    private TemperatureUnitEnum temperaturaUnit = TemperatureUnitEnum.CELSIUS;
    private Double lastLocationLatitude;
    private Double lastLocationLongitude;

    public Double getLastLocationLatitude() {
        return lastLocationLatitude;
    }

    public void setLastLocationLatitude(Double lastLocationLatitude) {
        this.lastLocationLatitude = lastLocationLatitude;
    }

    public Double getLastLocationLongitude() {
        return lastLocationLongitude;
    }

    public void setLastLocationLongitude(Double lastLocationLongitude) {
        this.lastLocationLongitude = lastLocationLongitude;
    }

    public TemperatureUnitEnum getTemperaturaUnit() {
        return temperaturaUnit;
    }

    public void setTemperaturaUnit(TemperatureUnitEnum temperaturaUnit) {
        this.temperaturaUnit = temperaturaUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserPreference that = (UserPreference) o;

        return new EqualsBuilder()
                .append(temperaturaUnit, that.temperaturaUnit)
                .append(lastLocationLatitude, that.lastLocationLatitude)
                .append(lastLocationLongitude, that.lastLocationLongitude)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(temperaturaUnit)
                .append(lastLocationLatitude)
                .append(lastLocationLongitude)
                .toHashCode();
    }
}
