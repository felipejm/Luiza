package br.com.luizalabs.luizalabs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Singleton;

import br.com.luizalabs.luizalabs.weather.api.WeatherApi;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@AppScope
@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    App provideApp() {
        return app;
    }

    @Provides
    public WeatherApi provideWeatherApi(OkHttpClient okHttpClient){
        WeatherApi weatherApi = new Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WeatherApi.class);

        return weatherApi;
    }

    @Provides
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor, Interceptor weatherApiInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(weatherApiInterceptor)
                .build();
    }

    @Provides
    public Gson provideGson(){
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    @Provides
    public SharedPreferences provideSharePreference(){
        return app.getApplicationContext().getSharedPreferences("LuizaLabs",Context.MODE_PRIVATE);
    }

    @Provides
    public Interceptor provideWeatherApiInterceptor() {
        return chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("appid", BuildConfig.WEATHER_API_KEY)
                    .addQueryParameter("lang", Locale.getDefault().getLanguage())
                    .addQueryParameter("units", "metric")
                    .build();

            Request request = original.newBuilder().url(url).build();
            return chain.proceed(request);
        };
    }

    @Provides
    public HttpLoggingInterceptor provideLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
