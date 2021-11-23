package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.AdapterModule;
import com.workflow.presentation.di.modules.WorkAssignModule;
import com.workflow.presentation.di.modules.WorkAssignedRepositoryModule;
import com.workflow.presentation.di.modules.WorkerWorkModule;
import com.workflow.presentation.view.activities.WorkAssignActivity;
import com.workflow.presentation.view.activities.WorkerWorkAssignActivity;

import dagger.Component;

/**
 * Created by Michael on 06/07/19.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class
        , modules = { WorkAssignModule.class, WorkAssignedRepositoryModule.class })
public interface WorkAssignComponent {
    void inject(WorkAssignActivity activity);
}
