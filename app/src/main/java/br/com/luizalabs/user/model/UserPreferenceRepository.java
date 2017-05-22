package br.com.luizalabs.user.model;


public interface UserPreferenceRepository {
    void save(UserPreference userPreference);
    UserPreference get();
}

