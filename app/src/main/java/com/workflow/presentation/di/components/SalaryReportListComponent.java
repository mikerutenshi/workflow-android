package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.SalaryReportListModule;
import com.workflow.presentation.di.modules.SalaryReportRepositoryModule;
import com.workflow.presentation.view.fragments.SalaryReportListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class,
        modules = {SalaryReportListModule.class, SalaryReportRepositoryModule.class })
public interface SalaryReportListComponent {
    void inject(SalaryReportListFragment fragment);
}
