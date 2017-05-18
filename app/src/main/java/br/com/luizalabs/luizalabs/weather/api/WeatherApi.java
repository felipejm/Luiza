package br.com.luizalabs.luizalabs.weather.api;


import br.com.luizalabs.luizalabs.weather.api.ApiWeather;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("data/2.5/weather")
    Observable<ApiWeather> getForCity(@Query("id") int cityId);


}
