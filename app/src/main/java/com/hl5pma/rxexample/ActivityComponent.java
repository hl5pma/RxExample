package com.hl5pma.rxexample;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent extends AppComponent {
    void inject(MainActivity activity);
}
