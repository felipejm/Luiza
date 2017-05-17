package br.com.luizalabs.luizalabs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private  MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides @Singleton
    public Gson provideGson(){
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }
}
