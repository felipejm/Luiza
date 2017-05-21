package br.com.luizalabs.user.model;


import br.com.luizalabs.weather.model.TemperatureUnitEnum;

public interface UserPreferenceInteractor {
    boolean hasLastLocation();
    void saveLastLocation(double latitude, double longitude);
    void saveTemperatureUnit(TemperatureUnitEnum temperaturaUnit);

    UserPreference get();
}
