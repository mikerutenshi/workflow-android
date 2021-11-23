package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.WorkListModule;
import com.workflow.presentation.view.fragments.WorkListFragment;

import dagger.Component;

/**
 * Created by Michael on 28/06/19.
 */

@PerFragment
@Component(dependencies = MainComponent.class, modules = WorkListModule.class)
public interface WorkListComponent {
    void inject(WorkListFragment fragment);
}
