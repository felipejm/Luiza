package br.com.luizalabs.weather.views.map;

import br.com.luizalabs.AppComponent;
import br.com.luizalabs.AppScope;
import dagger.Component;

@AppScope
@Component(
        dependencies = AppComponent.class,
        modules = WeatherMapModule.class
)
public interface WeatherMapComponent {
    void inject(WeatherMapFragment fragment);
}
