package br.com.luizalabs.weather.views;

import br.com.luizalabs.AppComponent;
import br.com.luizalabs.AppScope;
import dagger.Component;

@AppScope
@Component(
        dependencies = AppComponent.class,
        modules = WeatherModule.class
)
public interface WeatherComponent {
    void inject(WeatherActivity activity);
}
