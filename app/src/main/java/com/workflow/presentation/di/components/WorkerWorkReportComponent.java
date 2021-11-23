package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.AdapterModule;
import com.workflow.presentation.di.modules.WorkerWorkModule;
import com.workflow.presentation.di.modules.WorkerWorkReportModule;
import com.workflow.presentation.view.fragments.WorkerWorkReportFragment;

import dagger.Component;

/**
 * Created by Michael on 10/07/19.
 */

@PerFragment
@Component(dependencies = MainComponent.class, modules = { WorkerWorkReportModule.class, WorkerWorkModule.class, AdapterModule.class })
public interface WorkerWorkReportComponent {
    void inject(WorkerWorkReportFragment fragment);
}
