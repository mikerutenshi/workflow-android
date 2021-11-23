package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.AdapterModule;
import com.workflow.presentation.di.modules.WorkDoneModule;
import com.workflow.presentation.di.modules.WorkDoneRepositoryModule;
import com.workflow.presentation.di.modules.WorkerWorkModule;
import com.workflow.presentation.view.fragments.WorkDoneFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = { WorkDoneModule.class, WorkDoneRepositoryModule.class})
public interface WorkDoneComponent {
    void inject(WorkDoneFragment fragment);
}
