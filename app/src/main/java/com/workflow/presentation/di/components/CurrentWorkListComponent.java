package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.CurrentWorkListModule;
import com.workflow.presentation.di.modules.CurrentWorkRepositoryModule;
import com.workflow.presentation.di.modules.WorkModule;
import com.workflow.presentation.view.fragments.CurrentWorkListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules =
        { CurrentWorkListModule.class, CurrentWorkRepositoryModule.class })
public interface CurrentWorkListComponent {
    void inject(CurrentWorkListFragment fragment);
}
