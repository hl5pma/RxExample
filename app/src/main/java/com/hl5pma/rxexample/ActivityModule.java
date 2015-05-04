package com.hl5pma.rxexample;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        addsTo = AppModule.class,
        injects = {
                MainActivity.class
        },
        overrides = true,
        library = true
)
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides @Singleton AppCompatActivity provideActivity() {
        return mActivity;
    }
}
