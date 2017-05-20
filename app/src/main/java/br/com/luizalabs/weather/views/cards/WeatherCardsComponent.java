package br.com.luizalabs.weather.views.cards;

import br.com.luizalabs.AppComponent;
import br.com.luizalabs.AppScope;
import dagger.Component;

@AppScope
@Component(
        dependencies = AppComponent.class,
        modules = WeatherCardsModule.class
)
public interface WeatherCardsComponent {
    void inject(WeatherCardsFragment fragment);
}
