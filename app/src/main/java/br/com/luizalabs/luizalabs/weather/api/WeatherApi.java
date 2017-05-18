package br.com.luizalabs.luizalabs.weather.api;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("/data/2.5/box/city")
    Observable<ApiWeathers> getForArea(@Query("bbox") String area);


}
