package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.CurrentWorkDetailAssignedModule;
import com.workflow.presentation.di.modules.CurrentWorkRepositoryModule;
import com.workflow.presentation.view.fragments.CurrentWorkDetailAssignedFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules =
        {CurrentWorkDetailAssignedModule.class, CurrentWorkRepositoryModule.class })
public interface CurrentWorkDetailAssignedComponent {
    void inject(CurrentWorkDetailAssignedFragment fragment);
}
