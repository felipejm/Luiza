package br.com.luizalabs.user.model;


import java.io.Serializable;

import br.com.luizalabs.weather.model.TEMPERATURA_UNIT;

public class UserPreference implements Serializable {

    private TEMPERATURA_UNIT temperaturaUnit = TEMPERATURA_UNIT.CELSIUS;
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

    public TEMPERATURA_UNIT getTemperaturaUnit() {
        return temperaturaUnit;
    }

    public void setTemperaturaUnit(TEMPERATURA_UNIT temperaturaUnit) {
        this.temperaturaUnit = temperaturaUnit;
    }
}
