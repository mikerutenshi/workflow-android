package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.WorkerListModule;
import com.workflow.presentation.di.modules.WorkerModule;
import com.workflow.presentation.view.fragments.WorkerListFragment;

import dagger.Component;

/**
 * Created by Michael on 28/06/19.
 */

@PerFragment
@Component(dependencies = MainComponent.class, modules = WorkerListModule.class)
public interface WorkerComponent {
    void inject(WorkerListFragment fragment);
}
