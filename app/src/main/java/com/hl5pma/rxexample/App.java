package com.hl5pma.rxexample;

import android.app.Application;

public class App extends Application {

    private AppComponent mAppComponent;

    @Override public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.create();
    }

    public AppComponent component() {
        return mAppComponent;
    }
}
