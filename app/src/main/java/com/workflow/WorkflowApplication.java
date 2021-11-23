package com.workflow;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.workflow.presentation.di.components.ApplicationComponent;
import com.workflow.presentation.di.components.DaggerApplicationComponent;
import com.workflow.presentation.di.modules.ApplicationModule;

import timber.log.Timber;

/**
 * Created by Michael on 29/05/19.
 */

public class WorkflowApplication extends Application {

    protected ApplicationComponent applicationComponent;
    private static WorkflowApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDagger();
        initLeakDetection();
        initTimber();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    private void initLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }
}
