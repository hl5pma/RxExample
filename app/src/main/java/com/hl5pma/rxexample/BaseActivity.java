package com.hl5pma.rxexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = (ActivityComponent) getLastCustomNonConfigurationInstance();
        if (mActivityComponent == null) {
            App app = (App) getApplication();
            mActivityComponent = DaggerActivityComponent.builder()
                    .appComponent(app.component())
                    .build();
        }
    }

    protected ActivityComponent component() {
        return mActivityComponent;
    }

    @Override public Object onRetainCustomNonConfigurationInstance() {
        return mActivityComponent;
    }
}
