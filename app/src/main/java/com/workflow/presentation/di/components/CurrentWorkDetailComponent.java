package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.CurrentWorkDetailModule;
import com.workflow.presentation.view.activities.WorkDetailActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        CurrentWorkDetailModule.class
})
public interface CurrentWorkDetailComponent {
    void inject(WorkDetailActivity workDetailActivity);
}
