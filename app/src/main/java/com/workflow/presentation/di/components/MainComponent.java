package com.workflow.presentation.di.components;

import android.content.SharedPreferences;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.AuthModule;
import com.workflow.presentation.di.modules.MainActivityModule;
import com.workflow.presentation.view.activities.MainActivity;

import dagger.Component;

/**
 * Created by Michael on 10/06/19.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
    SharedPreferences sharedPreferences();
}
