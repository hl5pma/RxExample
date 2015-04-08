package com.hl5pma.rxexample;

import android.support.v7.app.ActionBarActivity;

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

    private ActionBarActivity mActivity;

    public ActivityModule(ActionBarActivity activity) {
        mActivity = activity;
    }

    @Provides @Singleton ActionBarActivity provideActivity() {
        return mActivity;
    }
}
