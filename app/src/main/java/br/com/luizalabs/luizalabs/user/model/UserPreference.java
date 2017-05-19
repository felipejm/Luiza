package br.com.luizalabs.luizalabs.user.model;


import java.io.Serializable;

import br.com.luizalabs.luizalabs.weather.model.TEMPERATURA_UNIT;

public class UserPreference implements Serializable{

    private TEMPERATURA_UNIT temperaturaUnit = TEMPERATURA_UNIT.CELSIUS;

    public TEMPERATURA_UNIT getTemperaturaUnit() {
        return temperaturaUnit;
    }

    public void setTemperaturaUnit(TEMPERATURA_UNIT temperaturaUnit) {
        this.temperaturaUnit = temperaturaUnit;
    }
}
