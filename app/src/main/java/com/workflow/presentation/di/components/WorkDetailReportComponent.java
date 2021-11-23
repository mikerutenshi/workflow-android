package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.AdapterModule;
import com.workflow.presentation.di.modules.ReportWorkerDetailModule;
import com.workflow.presentation.di.modules.WorkDetailReportModule;
import com.workflow.presentation.view.activities.ReportWorkerDetailActivity;

import dagger.Component;

/**
 * Created by Michael on 25/07/19.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = { ReportWorkerDetailModule.class, WorkDetailReportModule.class, AdapterModule.class })
public interface WorkDetailReportComponent {
    void inject(ReportWorkerDetailActivity activity);
}
