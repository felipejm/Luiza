package br.com.luizalabs.weather.views.cards;

import br.com.luizalabs.app.AppComponent;
import br.com.luizalabs.app.AppScope;
import dagger.Component;

@AppScope
@Component(
        dependencies = AppComponent.class,
        modules = WeatherCardsModule.class
)
public interface WeatherCardsComponent {
    void inject(WeatherCardsFragment fragment);
}
