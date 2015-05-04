package com.hl5pma.rxexample;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    DaumOpenApi daumOpenApi();
}
