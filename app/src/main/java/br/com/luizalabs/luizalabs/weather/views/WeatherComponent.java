package br.com.luizalabs.luizalabs.weather.views;

import javax.inject.Singleton;

import br.com.luizalabs.luizalabs.AppComponent;
import br.com.luizalabs.luizalabs.AppScope;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsFragment;
import br.com.luizalabs.luizalabs.weather.views.cards.WeatherCardsModule;
import dagger.Component;

@AppScope
@Component(
        dependencies = AppComponent.class,
        modules = WeatherModule.class
)
public interface WeatherComponent {
    void inject(WeatherActivity activity);
}
