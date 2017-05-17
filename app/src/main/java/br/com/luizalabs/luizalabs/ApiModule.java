package br.com.luizalabs.luizalabs;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {

    public WeatherApi provideWeatherApi(){
        Retrofit retrofit = getRetrofit(BuildConfig.WEATHER_API_URL);
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        return weatherApi;
    }

    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
    }
}
