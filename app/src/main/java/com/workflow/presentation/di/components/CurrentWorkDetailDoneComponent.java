package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.CurrentWorkDetailDoneModule;
import com.workflow.presentation.di.modules.CurrentWorkRepositoryModule;
import com.workflow.presentation.view.fragments.CurrentWorkDetailDoneFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules =
        {CurrentWorkDetailDoneModule.class, CurrentWorkRepositoryModule.class })
public interface CurrentWorkDetailDoneComponent {
    void inject(CurrentWorkDetailDoneFragment fragment);
}
