package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.SalaryReportDetailModule;
import com.workflow.presentation.di.modules.SalaryReportRepositoryModule;
import com.workflow.presentation.view.activities.SalaryReportDetailActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = { SalaryReportDetailModule.class, SalaryReportRepositoryModule.class })
public interface SalaryReportDetailComponent {
    void inject(SalaryReportDetailActivity activity);
}
