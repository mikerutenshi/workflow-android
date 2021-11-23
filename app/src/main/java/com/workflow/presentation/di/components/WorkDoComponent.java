package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.WorkDoModule;
import com.workflow.presentation.di.modules.WorkDoneRepositoryModule;
import com.workflow.presentation.view.activities.WorkDoneActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        WorkDoModule.class, WorkDoneRepositoryModule.class
})
public interface WorkDoComponent {
    void inject(WorkDoneActivity activity);
}
