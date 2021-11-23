package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.AdapterModule;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.WorkListModule;
import com.workflow.presentation.di.modules.WorkerDetailModule;
import com.workflow.presentation.di.modules.WorkerWorkModule;
import com.workflow.presentation.view.activities.WorkerDetailActivity;

import dagger.Component;

/**
 * Created by Michael on 01/07/19.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = { WorkerDetailModule.class })
public interface WorkerDetailComponent {
    void inject(WorkerDetailActivity activity);
}
