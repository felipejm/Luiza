package br.com.luizalabs.luizalabs.weather.views.cards;

import br.com.luizalabs.luizalabs.AppComponent;
import dagger.Component;

@Component(
        dependencies = AppComponent.class,
        modules = WeatherCardsModule.class
)
public interface WeatherCardsComponent {
    void inject(WeatherCardsFragment activity);
}
