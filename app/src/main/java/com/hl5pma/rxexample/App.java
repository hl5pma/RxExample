package com.hl5pma.rxexample;

import android.app.Application;

import dagger.ObjectGraph;

public class App extends Application {

    private ObjectGraph mAppGraph;

    @Override public void onCreate() {
        super.onCreate();
        mAppGraph = ObjectGraph.create(getModules());
    }

    public Object[] getModules() {
        return new Object[]{new AppModule()};
    }

    public ObjectGraph getAppGraph() {
        return mAppGraph;
    }
}
