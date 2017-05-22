package br.com.luizalabs.user.model;


import android.content.SharedPreferences;

import com.google.gson.Gson;

public class UserPreferencePreferenceRepositoryImpl implements UserPreferenceRepository {

    public static final String USER_PREFERENCES = "userPreferences";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public UserPreferencePreferenceRepositoryImpl(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    @Override
    public void save(UserPreference userPreference) {
        String json = gson.toJson(userPreference);
        sharedPreferences.edit().putString(USER_PREFERENCES, json).apply();
    }

    @Override
    public UserPreference get() {
        String json = sharedPreferences.getString(USER_PREFERENCES, "{}");
        return gson.fromJson(json, UserPreference.class);
    }
}
