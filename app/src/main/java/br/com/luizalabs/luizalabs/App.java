package br.com.luizalabs.luizalabs;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;


public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        createAppComponent();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private void createAppComponent(){
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
