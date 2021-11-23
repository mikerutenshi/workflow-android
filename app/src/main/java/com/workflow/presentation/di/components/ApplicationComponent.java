package com.workflow.presentation.di.components;

import android.content.SharedPreferences;

import com.workflow.WorkflowApplication;
import com.workflow.presentation.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Michael on 29/05/19.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(WorkflowApplication workflowApplication);
    SharedPreferences sharedPreferences();
}
