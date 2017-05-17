package br.com.luizalabs.luizalabs;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("data/2.5/apiWeatherInfo")
    Observable<ApiWeather> getForCity(@Query("id") int cityId, @Query("appid") String apiKey);


}
