package com.workflow.presentation.di.components;

import android.content.SharedPreferences;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.view.activities.AuthActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface AuthComponent {
    void inject(AuthActivity authActivity);
    SharedPreferences sharedPreferences();
}
