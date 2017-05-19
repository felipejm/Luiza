package br.com.luizalabs.luizalabs.weather.views.cards;

import javax.inject.Singleton;

import br.com.luizalabs.luizalabs.AppComponent;
import br.com.luizalabs.luizalabs.AppScope;
import dagger.Component;

@AppScope
@Component(
        dependencies = AppComponent.class,
        modules = WeatherCardsModule.class
)
public interface WeatherCardsComponent {
    void inject(WeatherCardsFragment fragment);
}
