package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.salary_report.GetSalaryReportList;
import com.workflow.presentation.presenter.SalaryReportListPresenter;
import com.workflow.presentation.presenter.SalaryReportListPresenterImpl;
import com.workflow.presentation.view.SalaryReportListView;
import com.workflow.presentation.view.adapters.SalaryReportListAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = { AdapterModule.class })
public class SalaryReportListModule {

    private final SalaryReportListView view;

    public SalaryReportListModule(SalaryReportListView view) {
        this.view = view;
    }

    @Provides SalaryReportListAdapter adapter(Context context) {
        return new SalaryReportListAdapter(context);
    }

    @Provides SalaryReportListPresenter presenter(GetSalaryReportList getSalaryReportList) {
        return new SalaryReportListPresenterImpl(getSalaryReportList, view);
    }
}
