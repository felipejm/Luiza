package br.com.luizalabs.luizalabs;

import dagger.Component;

@Component(
        dependencies = AppComponent.class,
        modules = MainModule.class
)
public interface MainComponent {
    void inject(MainActivity activity);
}
