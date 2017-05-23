package br.com.luizalabs.user.model;

import br.com.luizalabs.weather.model.TemperatureUnitEnum;

public class UserPreferenceTestUtil {

    public static UserPreference create(){
        return create(-8.05428, -34.8813, TemperatureUnitEnum.CELSIUS);
    }

    public static UserPreference create(double lat, double lon){
        return create(lat, lon, TemperatureUnitEnum.CELSIUS);
    }

    public static UserPreference create(TemperatureUnitEnum temperatureUnitEnum){
        return create(-8.05428, -34.8813, temperatureUnitEnum);
    }

    public static UserPreference create(double lat, double lon, TemperatureUnitEnum temperatureUnitEnum){
        UserPreference userPreference = new UserPreference();
        userPreference.setLastLocationLatitude(lat);
        userPreference.setLastLocationLongitude(lon);
        userPreference.setTemperaturaUnit(temperatureUnitEnum);
        return userPreference;
    }

}
