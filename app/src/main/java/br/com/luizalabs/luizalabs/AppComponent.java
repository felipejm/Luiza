package br.com.luizalabs.luizalabs;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(App app);
}
