package br.com.luizalabs.weather.views.cards;

import java.util.List;

import br.com.luizalabs.weather.model.Weather;

public interface WeatherCardsView {
    void configureWeatherCards(List<Weather> weathers);
}
