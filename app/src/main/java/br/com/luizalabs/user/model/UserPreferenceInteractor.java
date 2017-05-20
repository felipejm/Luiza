package br.com.luizalabs.user.model;


import br.com.luizalabs.weather.model.TEMPERATURA_UNIT;

public interface UserPreferenceInteractor {
    boolean hasLastLocation();

    void saveLastLocation(double latitude, double longitude);

    void saveTemperatureUnit(TEMPERATURA_UNIT temperaturaUnit);

    void save(UserPreference userPreference);

    UserPreference get();
}
