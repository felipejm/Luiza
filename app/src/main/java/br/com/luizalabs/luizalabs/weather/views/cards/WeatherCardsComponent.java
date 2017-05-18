package br.com.luizalabs.luizalabs.weather.views.cards;

import br.com.luizalabs.luizalabs.AppComponent;
import br.com.luizalabs.luizalabs.AppScope;
import dagger.Component;

@Component(
        dependencies = AppComponent.class,
        modules = WeatherCardsModule.class
)
public interface WeatherCardsComponent {
    void inject(WeatherCardsActivity activity);
}
