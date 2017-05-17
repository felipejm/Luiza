package br.com.luizalabs.luizalabs;

import android.app.Application;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new DaggerAppComponent();

    }
}
