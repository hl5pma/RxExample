package com.hl5pma.rxexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dagger.ObjectGraph;

public abstract class BaseActivity extends AppCompatActivity {

    private ObjectGraph mActivityGraph;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityGraph = (ObjectGraph) getLastCustomNonConfigurationInstance();
        if (mActivityGraph == null) {
            App app = (App) getApplication();
            mActivityGraph = app.getAppGraph().plus(new ActivityModule(this));
        } else {
            mActivityGraph = mActivityGraph.plus(new ActivityModule(this));
        }
        mActivityGraph.inject(this);
    }

    @Override public Object onRetainCustomNonConfigurationInstance() {
        return mActivityGraph;
    }
}
