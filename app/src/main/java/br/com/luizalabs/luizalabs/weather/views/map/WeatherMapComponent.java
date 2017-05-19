package br.com.luizalabs.luizalabs.weather.views.map;

import br.com.luizalabs.luizalabs.AppComponent;
import br.com.luizalabs.luizalabs.AppScope;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsFragment;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsModule;
import dagger.Component;

@AppScope
@Component(
        dependencies = AppComponent.class,
        modules = WeatherMapModule.class
)
public interface WeatherMapComponent {
    void inject(WeatherMapFragment fragment);
}
