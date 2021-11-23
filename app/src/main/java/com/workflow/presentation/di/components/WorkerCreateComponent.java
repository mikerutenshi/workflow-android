package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.ValidatorModule;
import com.workflow.presentation.di.modules.WorkerCreateModule;
import com.workflow.presentation.view.activities.WorkerCreateActivity;

import dagger.Component;

/**
 * Created by Michael on 29/06/19.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = WorkerCreateModule.class)
public interface WorkerCreateComponent {
    void inject(WorkerCreateActivity activity);
}
