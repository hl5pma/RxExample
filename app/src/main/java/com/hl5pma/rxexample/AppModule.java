package com.hl5pma.rxexample;

import dagger.Module;

@Module(
        injects = {App.class},
        includes = {ApiModule.class}
)
public class AppModule {
}
