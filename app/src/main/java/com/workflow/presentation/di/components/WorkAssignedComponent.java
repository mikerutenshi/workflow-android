package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.AdapterModule;
import com.workflow.presentation.di.modules.WorkAssignedModule;
import com.workflow.presentation.di.modules.WorkAssignedRepositoryModule;
import com.workflow.presentation.di.modules.WorkerWorkModule;
import com.workflow.presentation.view.fragments.WorkAssignedFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules =
        { WorkAssignedModule.class, WorkAssignedRepositoryModule.class })
public interface WorkAssignedComponent {
    void inject(WorkAssignedFragment fragment);
}
