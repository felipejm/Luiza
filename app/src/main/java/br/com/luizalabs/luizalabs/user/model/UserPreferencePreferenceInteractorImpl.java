package br.com.luizalabs.luizalabs.user.model;


import android.content.SharedPreferences;

import com.google.gson.Gson;

public class UserPreferencePreferenceInteractorImpl implements UserPreferenceInteractor {

    public static final String USER_PREFERENCES = "userPreferences";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public UserPreferencePreferenceInteractorImpl(Gson gson, SharedPreferences sharedPreferences) {
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void save(UserPreference userPreference){
        String json = gson.toJson(userPreference);
        sharedPreferences.edit().putString(USER_PREFERENCES, json).commit();
    }

    @Override
    public UserPreference get(){
        String json = sharedPreferences.getString(USER_PREFERENCES, "{}");
        return gson.fromJson(json, UserPreference.class);
    }
}
