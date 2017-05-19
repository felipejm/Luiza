package br.com.luizalabs.luizalabs.user.model;


public interface UserPreferenceInteractor {
    void save(UserPreference userPreference);

    UserPreference get();
}
