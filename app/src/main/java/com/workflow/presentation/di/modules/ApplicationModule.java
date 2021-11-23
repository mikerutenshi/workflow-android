package com.workflow.presentation.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.workflow.presentation.di.interfaces.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 29/05/19.
 */

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences("workflow_preferences", Context.MODE_PRIVATE);
    }
}
