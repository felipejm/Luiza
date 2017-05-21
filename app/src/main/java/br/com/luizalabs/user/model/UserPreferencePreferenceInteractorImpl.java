package br.com.luizalabs.user.model;


import br.com.luizalabs.weather.model.TemperatureUnitEnum;

public class UserPreferencePreferenceInteractorImpl implements UserPreferenceInteractor {

    private UserPreferenceRepository repository;

    public UserPreferencePreferenceInteractorImpl(UserPreferenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean hasLastLocation() {
        UserPreference userPreference = repository.get();
        return userPreference.getLastLocationLatitude() != null && userPreference.getLastLocationLongitude() != null;
    }

    @Override
    public void saveLastLocation(double latitude, double longitude) {
        UserPreference userPreference = repository.get();
        userPreference.setLastLocationLatitude(latitude);
        userPreference.setLastLocationLongitude(longitude);
        repository.save(userPreference);
    }

    @Override
    public void saveTemperatureUnit(TemperatureUnitEnum temperaturaUnit) {
        UserPreference userPreference = repository.get();
        userPreference.setTemperaturaUnit(temperaturaUnit);
        repository.save(userPreference);
    }

    @Override
    public UserPreference get() {
        return repository.get();
    }
}
