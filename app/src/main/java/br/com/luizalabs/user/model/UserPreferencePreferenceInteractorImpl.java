package br.com.luizalabs.user.model;


import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.luizalabs.weather.model.TEMPERATURA_UNIT;

public class UserPreferencePreferenceInteractorImpl implements UserPreferenceInteractor {

    public static final String USER_PREFERENCES = "userPreferences";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public UserPreferencePreferenceInteractorImpl(Gson gson, SharedPreferences sharedPreferences) {
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public boolean hasLastLocation() {
        UserPreference userPreference = get();
        return userPreference.getLastLocationLatitude() != null && userPreference.getLastLocationLongitude() != null;
    }

    @Override
    public void saveLastLocation(double latitude, double longitude) {
        UserPreference userPreference = get();
        userPreference.setLastLocationLatitude(latitude);
        userPreference.setLastLocationLongitude(longitude);
        save(userPreference);
    }

    @Override
    public void saveTemperatureUnit(TEMPERATURA_UNIT temperaturaUnit) {
        UserPreference userPreference = get();
        userPreference.setTemperaturaUnit(temperaturaUnit);
        save(userPreference);
    }

    @Override
    public void save(UserPreference userPreference) {
        String json = gson.toJson(userPreference);
        sharedPreferences.edit().putString(USER_PREFERENCES, json).commit();
    }

    @Override
    public UserPreference get() {
        String json = sharedPreferences.getString(USER_PREFERENCES, "{}");
        return gson.fromJson(json, UserPreference.class);
    }
}
